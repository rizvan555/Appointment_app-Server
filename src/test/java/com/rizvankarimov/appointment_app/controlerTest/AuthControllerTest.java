package com.rizvankarimov.appointment_app.controlerTest;

import com.rizvankarimov.appointment_app.controller.AuthenticationController;
import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.service.AuthenticationService;
import com.rizvankarimov.appointment_app.service.JwtRefreshTokenService;
import com.rizvankarimov.appointment_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtRefreshTokenService jwtRefreshTokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void testRegister_Success() {
        User testUser = new User();
        testUser.setUsername("rizvan");
        testUser.setPassword("123456");
        testUser.setPhone("015151400004");
        testUser.setEmail("rizvan84@gmx.de");

        when(userService.findByUsername("rizvan")).thenReturn(null); // Kullanıcı bulunamadığını simüle et

        ResponseEntity<?> responseEntity = authenticationController.register(testUser);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testRegister_Failure_UserExists() {
        User existingUser = new User();
        existingUser.setUsername("rizvan");
        existingUser.setPassword("123456");
        existingUser.setPhone("015151400004");
        existingUser.setEmail("rizvan84@gmx.de");

        // User tapildigini simule et
        when(userService.findByUsername("rizvan")).thenReturn(Optional.of(existingUser));

        // Register methodunu cagir
        ResponseEntity<?> responseEntity = authenticationController.register(existingUser);

        // Neticenin dogru oldugunu kontrol et
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }
}
