package com.rizvankarimov.appointment_app.controlerTest;

import com.rizvankarimov.appointment_app.controller.UserController;
import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.service.UserService;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    void checkGetWithId() {
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
}
