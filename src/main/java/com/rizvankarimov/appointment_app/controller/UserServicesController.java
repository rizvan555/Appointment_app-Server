package com.rizvankarimov.appointment_app.controller;

import com.rizvankarimov.appointment_app.entity.UserServices;
import com.rizvankarimov.appointment_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/userServices")
public class UserServicesController {

private final UserService userService;
    @PostMapping("addUserServices")
    public ResponseEntity<String> addUserServices(@RequestBody UserServices userServices) {
        userService.addUserServices(userServices);
        return ResponseEntity.ok("Service added successfully");
    }
}
