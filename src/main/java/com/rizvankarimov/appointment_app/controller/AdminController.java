package com.rizvankarimov.appointment_app.controller;

import com.rizvankarimov.appointment_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController
{
    private final UserService userService;

    @GetMapping("admin")
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.ok(userService.findAllUsers());
    }

}