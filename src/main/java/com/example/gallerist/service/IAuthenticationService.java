package com.example.gallerist.service;

import com.example.gallerist.dto.*;

public interface IAuthenticationService {

    public DtoUser register(LoginRequest input);

    public AuthResponse authenticate(AuthRequest input);

    public AuthResponse refreshToken(RefreshTokenRequest input);
}
