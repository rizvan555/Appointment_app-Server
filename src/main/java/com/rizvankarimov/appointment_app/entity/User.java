package com.rizvankarimov.appointment_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="users.html")
public class User
{
    private static final long serialVersionUID = 1L;
    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String username;

    private String phone;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    private LocalDateTime createDate;

    @Transient
    private String accessToken;

    @Transient
    private String refreshToken;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Enumerated(EnumType.STRING)
    private Role role;


    public Bean getRole() {
        return null;
    }
}