package com.rizvankarimov.appointment_app.controlerTest;

import com.rizvankarimov.appointment_app.controller.ServiceController;
import com.rizvankarimov.appointment_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private ServiceController serviceController;

    @Test
    void getAllServicesTest(){
        UserService userService = mock(UserService.class);
        ServiceController serviceController = new ServiceController(userService);
        ResponseEntity<?> responseEntity = serviceController.getAllServices();
         assertEquals(200, responseEntity.getStatusCodeValue());
         verify(userService,times(1)).getAllServices();
    }
}
