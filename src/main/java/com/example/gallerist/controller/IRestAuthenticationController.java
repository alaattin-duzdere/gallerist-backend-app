package com.example.gallerist.controller;

import com.example.gallerist.dto.*;

public interface IRestAuthenticationController {
    public RootEntity<DtoUser> register(LoginRequest input);

    public RootEntity<AuthResponse> authenticate(AuthRequest input);

    public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest input);
}
