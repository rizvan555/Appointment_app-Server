package com.rizvankarimov.appointment_app.repository;


import com.rizvankarimov.appointment_app.entity.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String>
{
}
