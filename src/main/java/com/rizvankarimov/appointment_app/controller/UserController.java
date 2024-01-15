package com.rizvankarimov.appointment_app.controller;


import com.rizvankarimov.appointment_app.entity.Role;
import com.rizvankarimov.appointment_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.rizvankarimov.appointment_app.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id)
    {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping("change/{role}")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Role role)
    {
        userService.changeRole(role, userPrincipal.getUsername());

        return ResponseEntity.ok(true);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.ok(userService.findAllUsers());
    }


}
