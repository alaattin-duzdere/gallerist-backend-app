package com.example.gallerist.service.impl;

import com.example.gallerist.dto.*;
import com.example.gallerist.enums.Role;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.jwt.JwtService;
import com.example.gallerist.model.RefreshToken;
import com.example.gallerist.model.User;
import com.example.gallerist.repository.RefreshTokenRepository;
import com.example.gallerist.repository.UserRepository;
import com.example.gallerist.service.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationServiceImpl implements IAuthenticationService     {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    private User createUser(LoginRequest loginRequest) {
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(loginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        user.setRoles(loginRequest.getRoles());
        log.info("Creating user with username: {}", user.getUsername());
        log.info("Creating user with roles: {}", user.getRoles());
        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // 1 day expiration
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public DtoUser register(LoginRequest input) {
        User savedUser = userRepository.save(createUser(input)); // create and save the user

        log.info("User {} has been registered with roles: {}", savedUser.getUsername(), savedUser.getRoles() );

        DtoUser dtoUser = new DtoUser(); // convert User to DtoUser
        BeanUtils.copyProperties(savedUser, dtoUser);

        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<User> optionalUser = userRepository.findByUsername(input.getUsername());

            String accessToken = jwtService.generateToken(optionalUser.get());
            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optionalUser.get()));

            return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.Username_Or_Password_Invalid,e.getMessage()));
        }
    }

    public boolean isValidRefreshToken(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest input) {
        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(input.getRefreshToken());

        if (optRefreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.Refresh_Token_Not_Found,input.getRefreshToken()));
        }
        if (!isValidRefreshToken(optRefreshToken.get().getExpiredDate())){
            throw new BaseException(new ErrorMessage(MessageType.Refresh_Token_Expired,input.getRefreshToken()));
        }

        User user = optRefreshToken.get().getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = createRefreshToken(user);
        RefreshToken savedRefreshToken= refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }


}
