package com.rizvankarimov.appointment_app.controlerTest;

import com.rizvankarimov.appointment_app.controller.ServiceController;
import com.rizvankarimov.appointment_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private ServiceController serviceController;

    // Diese Methode ermöglicht das Testen des Abrufens aller Dienste.
    @Test
    void getAllServicesTest(){
        UserService userService = mock(UserService.class);
        ServiceController serviceController = new ServiceController(userService);
        ResponseEntity<?> responseEntity = serviceController.getAllServices();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService,times(1)).getAllServices();
    }

    @Test
    void getServiceByIdTest(){
        UserService userService = mock(UserService.class);
        ServiceController serviceController = new ServiceController(userService);
        ResponseEntity<?> responseEntity = serviceController.getServiceById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    // Diese Methode testet das Hinzufügen eines Dienstes.
    @Test
    void addServiceTest(){
        UserService userService = mock(UserService.class);
        ServiceController serviceController = new ServiceController(userService);
        ResponseEntity<?> responseEntity = serviceController.addService((null));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    // Diese Methode testet das Aktualisieren eines Dienstes.
    @Test
    void updateServiceTest(){
        UserService userService = mock(UserService.class);
        ServiceController serviceController = new ServiceController(userService);
        ResponseEntity<?> responseEntity = serviceController.updateService(1L);
    }
}
