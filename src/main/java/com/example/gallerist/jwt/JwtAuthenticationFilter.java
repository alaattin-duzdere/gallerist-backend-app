package com.example.gallerist.jwt;

import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService ;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header== null){
            filterChain.doFilter(request,response);
            return;
        }
        String token ;
        String username;

        token = header.substring(7);

        try{
            username = jwtService.getUsernameByToken(token);
            if(username!=null || SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null || jwtService.isTokenValid(token)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    logger.info("SecurityContextHolder updated with user: " + username);
                    logger.info("Authorization header:"+ header);
                    logger.info("SecurityContextHolder updated with authtoken: " + authenticationToken);
                }
            }
        }
        catch (ExpiredJwtException ex){
            throw new BaseException(new ErrorMessage(MessageType.Token_Expired,ex.getMessage()));
        }
        catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.General_Exception,e.getMessage()));
        }
        filterChain.doFilter(request, response);
    }
}
