package com.rizvankarimov.appointment_app.controller;


import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.service.AuthenticationService;
import com.rizvankarimov.appointment_app.service.JwtRefreshTokenService;
import com.rizvankarimov.appointment_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author sa
 * @date 23.07.2023
 * @time 15:00
 */
@RestController
@RequestMapping("api/authentication")//pre-path
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController
{
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtRefreshTokenService jwtRefreshTokenService;

    @PostMapping("register")
    public ResponseEntity<?> signUp(@RequestBody User user)
    {
        if (userService.findByUsername(user.getUsername()).isPresent())
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("login")//api/authentication/sign-in
    public ResponseEntity<?> signIn(@RequestBody User user)
    {
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }

    @PostMapping("refresh-token")//api/authentication/refresh-token?token=
    public ResponseEntity<?> refreshToken(@RequestParam String token)
    {
        return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(token));
    }
}