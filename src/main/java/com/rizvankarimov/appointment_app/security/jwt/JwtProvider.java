package com.rizvankarimov.appointment_app.security.jwt;


import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;


public interface JwtProvider
{

    String generateToken(UserPrincipal auth);

    String generateToken(com.rizvankarimov.appointment_app.security.UserPrincipal auth);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
