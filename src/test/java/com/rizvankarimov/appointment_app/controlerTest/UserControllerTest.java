package com.rizvankarimov.appointment_app.controlerTest;

import com.rizvankarimov.appointment_app.controller.UserController;
import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.security.UserPrincipal;
import com.rizvankarimov.appointment_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getUserWithIdTest() {
        // Mock UserService
        UserService userService = mock(UserService.class);

        // Test edilecek kullanıcı
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("rizvan");
        testUser.setPhone("015151400004");
        testUser.setEmail("rizvan@rizvan.de");

        // UserController oluştur
        UserController userController = new UserController(userService);

        // UserController kullanarak UserService'ten kullanıcıyı alma işlemi
        when(userService.findUserById(1L)).thenReturn(testUser);

        // UserController üzerinden getUserById metodunu çağır
        ResponseEntity<?> responseEntity = userController.getUserById(1L);

        // Beklenen sonuçları kontrol et
        assertEquals(200, responseEntity.getStatusCode().value()); // HTTP 200 OK
        assertEquals(testUser, responseEntity.getBody()); // Kullanıcı doğru mu?

        // UserService'ten findUserById metodunun UserController tarafından çağrıldığını kontrol et
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    void getAllUsersTest() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        ResponseEntity<?> responseEntity = userController.getAllUsers();

        assertEquals(200, responseEntity.getStatusCode().value());
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    void getAuthenticatedUserTest() {
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername(null);

        // Kimlik doğrulama bilgileri oluştur
        UserPrincipal userPrincipal = new UserPrincipal(testUser);

        // Doğrulama token'ı oluştur
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        Authentication authToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

        // Güvenlik bağlamı üzerinden doğrulama bilgilerini ayarla
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // UserService'ten findByUsername metodunun test kullanıcısını döndürmesini sağla
        when(userService.findByUsername(null)).thenReturn(Optional.of(testUser)); // Buradaki değişiklik yapıldı

        // UserController üzerinden getAuthenticatedUser metodunu çağır
        ResponseEntity<User> responseEntity = userController.getAuthenticatedUser();

        // Beklenen sonuçları kontrol et
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testUser, responseEntity.getBody());
        verify(userService, times(1)).findByUsername(null);

        // Güvenlik bağlamını temizle
        SecurityContextHolder.clearContext();
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
