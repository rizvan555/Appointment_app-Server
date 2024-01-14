package com.rizvankarimov.appointment_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/services")

public class ServiceController {
    @PostMapping("addService")
    public ResponseEntity<?> addService() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("allServices")
    public ResponseEntity<?> getAllServices(){
        return ResponseEntity.ok(true);
    }
}
