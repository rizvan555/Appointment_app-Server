package com.rizvankarimov.appointment_app.service;


import com.rizvankarimov.appointment_app.entity.JwtRefreshToken;
import com.rizvankarimov.appointment_app.entity.User;

public interface JwtRefreshTokenService
{
    JwtRefreshToken createRefreshToken(Long userId);

    User generateAccessTokenFromRefreshToken(String refreshTokenId);
}
