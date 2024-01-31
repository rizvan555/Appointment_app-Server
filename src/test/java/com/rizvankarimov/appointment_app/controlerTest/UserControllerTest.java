package com.rizvankarimov.appointment_app.controlerTest;

import com.rizvankarimov.appointment_app.controller.UserController;
import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.security.UserPrincipal;
import com.rizvankarimov.appointment_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    void getUserWithIdTest() {
        // Mock UserService
        UserService userService = mock(UserService.class);

        // Test edilecek kullanıcı
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("rizvan");
        testUser.setPhone("111111");
        testUser.setEmail("rizvan@rizvan.de");

        // UserController oluştur
        UserController userController = new UserController(userService);

        // UserController kullanarak UserService'ten kullanıcıyı alma işlemi
        when(userService.findUserById(1L)).thenReturn(testUser);

        // UserController üzerinden getUserById metodunu çağır
        ResponseEntity<?> responseEntity = userController.getUserById(1L);

        // Beklenen sonuçları kontrol et
        assertEquals(200, responseEntity.getStatusCodeValue()); // HTTP 200 OK
        assertEquals(testUser, responseEntity.getBody()); // Kullanıcı doğru mu?

        // UserService'ten findUserById metodunun UserController tarafından çağrıldığını kontrol et
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    void getAllUsersTest() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        ResponseEntity<?> responseEntity = userController.getAllUsers();

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    void getAuthenticatedUserTest() {
        UserService userService = mock(UserService.class);
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("rizvan");

        // Authenticated user qur
        UserPrincipal userPrincipal = new UserPrincipal(testUser);

        // Authentication mock'unu qur
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, null);

        // SecurityContextHolder üzerinden Authentication mock'unu ayarladiq
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // UserService'ten findByUsername metodu çağrıldığında testUser'ı döndür
        when(userService.findByUsername("rizvan")).thenReturn(Optional.of(testUser));

        // UserController oluştur
        UserController userController = new UserController(userService);

        // UserController üzerinden getAuthenticatedUser metodunu çağır
        ResponseEntity<User> responseEntity = userController.getAuthenticatedUser();

        //Gözlenen neticeleri kontrol et
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testUser, responseEntity.getBody());
        verify(userService, times(1)).findByUsername("rizvan");
    }

    @Test
    void getUnauthenticatedUserTest() {
        UserService userService = mock(UserService.class);

        // Authentication mock'unu null olarak ayarla (doğrulanmamış kullanıcı)
        SecurityContextHolder.getContext().setAuthentication(null);

        // UserController oluştur
        UserController userController = new UserController(userService);

        // UserController üzerinden getAuthenticatedUser metodunu çağır
        ResponseEntity<User> responseEntity = userController.getAuthenticatedUser();

        // Beklenen sonuçları kontrol et
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode()); // HTTP 401 Unauthorized
        assertNull(responseEntity.getBody()); // Kullanıcı null olmalı
    }
}
