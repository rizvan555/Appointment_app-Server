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

    // Diese Methode ermöglicht das Testen des Abrufens eines Benutzers anhand seiner ID.
    @Test
    void getUserWithIdTest() {
        // Ein Mock-UserService wird erstellt.
        UserService userService = mock(UserService.class);

        // Zu testender Benutzer
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("rizvan");
        testUser.setPhone("015151400004");
        testUser.setEmail("rizvan@rizvan.de");

        // UserController wird erstellt
        UserController userController = new UserController(userService);

        // Benutzer abrufen über UserController
        when(userService.findUserById(1L)).thenReturn(testUser);

        // getUserById-Methode über UserController aufrufen
        ResponseEntity<?> responseEntity = userController.getUserById(1L);

        // Erwartete Ergebnisse überprüfen
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(testUser, responseEntity.getBody());

        // Überprüfen, ob die findUserById-Methode des UserService einmal aufgerufen wurde
        verify(userService, times(1)).findUserById(1L);
    }

    // Diese Methode ermöglicht das Testen des Abrufens aller Benutzer.
    @Test
    void getAllUsersTest() {
        // Ein Mock-UserService wird erstellt.
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        ResponseEntity<?> responseEntity = userController.getAllUsers();

        assertEquals(200, responseEntity.getStatusCode().value());
        verify(userService, times(1)).findAllUsers();
    }

    // Diese Methode ermöglicht das Testen des Abrufens des authentifizierten Benutzers.
    @Test
    void getAuthenticatedUserTest() {
        // Zu testender Benutzer
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername(null);

        // Benutzerprinzipal erstellen
        UserPrincipal userPrincipal = new UserPrincipal(testUser);

        // Authentifizierungstoken erstellen
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        Authentication authToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

        // Authentifizierungsinformationen im Sicherheitskontext setzen
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // UserService soll den Testbenutzer zurückgeben, wenn findByUsername aufgerufen wird
        when(userService.findByUsername(null)).thenReturn(Optional.of(testUser));

        // getAuthenticatedUser-Methode über UserController aufrufen
        ResponseEntity<User> responseEntity = userController.getAuthenticatedUser();

        // Erwartete Ergebnisse überprüfen
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testUser, responseEntity.getBody());
        verify(userService, times(1)).findByUsername(null);

        // Sicherheitskontext zurücksetzen
        SecurityContextHolder.clearContext();
    }

    // Diese Methode ermöglicht das Testen des Abrufens des nicht authentifizierten Benutzers.
    @Test
    void getUnauthenticatedUserTest() {
        // Ein Mock-UserService wird erstellt.
        UserService userService = mock(UserService.class);

        // Authentifizierungsmock auf null setzen (nicht authentifizierter Benutzer)
        SecurityContextHolder.getContext().setAuthentication(null);

        // UserController wird erstellt
        UserController userController = new UserController(userService);

        // getAuthenticatedUser-Methode über UserController aufrufen
        ResponseEntity<User> responseEntity = userController.getAuthenticatedUser();

        // Erwartete Ergebnisse überprüfen
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody()); // Benutzer sollte null sein
    }

}
