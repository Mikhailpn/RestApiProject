package ru.springsourse.RestApiProject.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.models.Measurement;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.repositories.SensorRepository;
import ru.springsourse.RestApiProject.services.SensorService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SensorServiceTest {

    @Mock
    SensorRepository sensorRepository;

    @InjectMocks
    SensorService sensorService;

    @Test
    void save_NotExistingSensor_ExecutesSave(){
        //given
        Sensor sensor = new Sensor(1, "Sensor 1", Collections.EMPTY_LIST);

        doReturn(Collections.EMPTY_LIST).when(sensorRepository).findByName(sensor.getName());

        //when
        this.sensorService.save(sensor);

        //then
        verify(sensorRepository).findByName(sensor.getName());
        verify(sensorRepository).save(sensor);
    }

    @Test
    void save_ExistingSensor_ThrowsException(){
        //given
        Sensor sensor = new Sensor(1, "Sensor 1", Collections.EMPTY_LIST);
        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(sensor);

        doReturn(sensorList).when(sensorRepository).findByName(sensor.getName());

        //when
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, ()->this.sensorService.save(sensor));

        //then
        verifyNoMoreInteractions(sensorRepository);
        assertTrue(thrown.getMessage().contains("Sensor with name " + sensor.getName() +  " already exists"));
    }
}
