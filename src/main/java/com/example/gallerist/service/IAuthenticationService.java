package com.example.gallerist.service;

import com.example.gallerist.dto.AuthRequest;
import com.example.gallerist.dto.AuthResponse;
import com.example.gallerist.dto.DtoUser;
import com.example.gallerist.dto.RefreshTokenRequest;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest input);

    public AuthResponse authenticate(AuthRequest input);

    public AuthResponse refreshToken(RefreshTokenRequest input);
}
