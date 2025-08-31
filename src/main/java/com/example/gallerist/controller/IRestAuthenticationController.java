package com.example.gallerist.controller;

import com.example.gallerist.dto.AuthRequest;
import com.example.gallerist.dto.AuthResponse;
import com.example.gallerist.dto.DtoUser;
import com.example.gallerist.dto.RefreshTokenRequest;

public interface IRestAuthenticationController {
    public RootEntity<DtoUser> register(AuthRequest input);

    public RootEntity<AuthResponse> authenticate(AuthRequest input);

    public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest input);
}
