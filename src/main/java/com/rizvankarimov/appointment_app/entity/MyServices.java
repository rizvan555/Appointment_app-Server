package com.rizvankarimov.appointment_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "services")
public class MyServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "selectedService", nullable = false)
    private String selectedService;

    @Column(name = "selectedTimeStart", nullable = false)
    private String selectedTimeStart;

}
