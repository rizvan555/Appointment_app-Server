package com.rizvankarimov.appointment_app.controller;

import com.rizvankarimov.appointment_app.entity.MyServices;
import com.rizvankarimov.appointment_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/services")

public class ServiceController {

    private final UserService userService;

    @PostMapping("addService")
    public ResponseEntity<String> addService(@RequestBody MyServices myServices) {
        userService.addService(myServices);
        return ResponseEntity.ok("Service added successfully");
    }


    @GetMapping("allServices")
    public ResponseEntity<?> getAllServices() {
        return ResponseEntity.ok(userService.getAllServices());
    }
}
