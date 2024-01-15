package com.rizvankarimov.appointment_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author sa
 * @date 23.07.2023
 * @time 14:03
 */
@Data
@Entity
@Table(name = "jwt_refresh_token")
public class JwtRefreshToken
{
    @Id
    @Column(name = "token", nullable = false)
    private String tokenId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;
}
