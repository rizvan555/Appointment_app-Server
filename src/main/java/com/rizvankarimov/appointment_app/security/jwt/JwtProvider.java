package com.rizvankarimov.appointment_app.security.jwt;


import com.rizvankarimov.appointment_app.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtProvider
{
    String generateToken(UserPrincipal auth);


    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
