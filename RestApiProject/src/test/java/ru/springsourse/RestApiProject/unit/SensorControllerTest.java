package ru.springsourse.RestApiProject.unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.controllers.SensorController;
import ru.springsourse.RestApiProject.dto.SensorDTO;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.services.SensorService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class SensorControllerTest {

    @Mock
    SensorService sensorService;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    SensorController sensorController;

    @Test
    void performRegistration_validSensor_returnsResponseEntityOK(){
        //given
        SensorDTO sensorDTO = new SensorDTO("Sensor 1");
        Sensor sensor = new Sensor(1, "Sensor 1", Collections.emptyList());
        doReturn(sensor).when(modelMapper).map(sensorDTO, Sensor.class);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        //when
        ResponseEntity response = this.sensorController.performRegistration(sensorDTO, bindingResult);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sensorService).save(sensor);
    }

    @Test
    void performRegistration_invalidSensor_throwsResponseStatusException(){
        //given
        SensorDTO sensorDTO = new SensorDTO("Sensor 1");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        //when
        assertThrows(ResponseStatusException.class, ()->this.sensorController.performRegistration(sensorDTO, bindingResult));

        //then
        verifyNoInteractions(sensorService);
    }


}
