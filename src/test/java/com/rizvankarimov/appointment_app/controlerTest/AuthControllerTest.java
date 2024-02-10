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


    // Dieser Test überprüft, ob die Benutzerregistrierung erfolgreich ist.
    @Test
    void testRegister_Success() {
        User testUser = new User();
        testUser.setUsername("rizvan");
        testUser.setPassword("123456");
        testUser.setPhone("015151400004");
        testUser.setEmail("rizvan84@gmx.de");

        // Es wird simuliert, dass der Benutzer nicht in der Datenbank vorhanden ist.
        when(userService.findByUsername("rizvan")).thenReturn(Optional.empty());

        // Die Registrierungsmethode wird aufgerufen.
        ResponseEntity<?> responseEntity = authenticationController.register(testUser);

        // Es wird überprüft, ob die zurückgegebene Antwort dem erwarteten Ergebnis entspricht.
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    // Dieser Test überprüft, ob ein Benutzer bereits existiert.
    @Test
    void testRegister_Failure_UserExists() {
        // Ein vorhandenes Benutzerbeispiel wird erstellt.
        User existingUser = new User();
        existingUser.setUsername("rizvan");
        existingUser.setPassword("123456");
        existingUser.setPhone("015151400004");
        existingUser.setEmail("rizvan84@gmx.de");

        // Es wird simuliert, dass der Benutzer in der Datenbank vorhanden ist.
        when(userService.findByUsername("rizvan")).thenReturn(Optional.of(existingUser));

        // Die Registrierungsmethode wird aufgerufen.
        ResponseEntity<?> responseEntity = authenticationController.register(existingUser);

        // Es wird überprüft, ob die zurückgegebene Antwort dem erwarteten Ergebnis entspricht.
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }
}
