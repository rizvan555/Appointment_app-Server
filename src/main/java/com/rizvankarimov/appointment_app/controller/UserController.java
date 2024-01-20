package com.rizvankarimov.appointment_app.controller;

import com.rizvankarimov.appointment_app.entity.Role;
import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.rizvankarimov.appointment_app.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        if (userPrincipal != null) {
            userService.changeRole(role, userPrincipal.getUsername());
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(500).body("UserPrincipal is null");
        }
    }

    @GetMapping("allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("authUser")
    public String getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserPrincipal) {
                String username = ((UserPrincipal) principal).getUsername();
                return "Authenticated User: " + username;
            } else {
                return "Authenticated User (Unknown): " + principal.toString();
            }
        } else {
            return "No authenticated user";
        }
    }

}
