package com.rizvankarimov.appointment_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_services")
public class UserServices {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "serviceName", unique = false, nullable = false, length = 255)
private String serviceName;

@Column(name = "date", nullable = false)
private String date;

@Column(name = "selectedTime", nullable = false)
private String selectedTime;

@Column(name = "userID", nullable = false)
private String userID;

}
