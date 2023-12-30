package ru.springsourse.RestApiProject.unit;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.models.Measurement;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.repositories.MeasurementRepository;
import ru.springsourse.RestApiProject.repositories.SensorRepository;
import ru.springsourse.RestApiProject.services.MeasurementService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementServiceTest {

    @Mock
    MeasurementRepository measurementRepository;

    @Mock
    SensorRepository sensorRepository;

    @InjectMocks
    MeasurementService measurementService;

    @Test
    void findAll_ReturnsValidMeasurements(){
        //given
        Sensor sensor = new Sensor(1, "Sensor 1", List.of());

        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(new Measurement(1, BigDecimal.valueOf(50), true, sensor, new Date()));
        measurementList.add(new Measurement(2, BigDecimal.valueOf(40), false, sensor, new Date()));
        sensor.setMeasurementList(measurementList);

        doReturn(measurementList).when(this.measurementRepository).findAll();

        //when
        var resMeasurementList = this.measurementService.findAll();

        //then
        assertNotNull(resMeasurementList);
        assertEquals(resMeasurementList.size(), 2);
        assertEquals(resMeasurementList, measurementList);

    }

    @Test
    void countRainy_ReturnsTrueCount(){
        //given
        Sensor sensor = new Sensor(1, "Sensor 1", List.of());

        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(new Measurement(1, BigDecimal.valueOf(50), true, sensor, new Date()));
        measurementList.add(new Measurement(2, BigDecimal.valueOf(40), true, sensor, new Date()));
        sensor.setMeasurementList(measurementList);

        doReturn(measurementList).when(this.measurementRepository).findByRaining(true);

        //when
        int resCount = this.measurementService.countRainy();

        //then
        assertEquals(resCount, 2);
    }

    @Test
    void add_ExistingSensor_ExecutesSave(){
        //given
        List<Sensor> sensorList = new ArrayList<>();
        Sensor  sensor = new Sensor(1, "Sensor 1", List.of());
        sensorList.add(sensor);
        Measurement measurement = (new Measurement(1, BigDecimal.valueOf(50), true, sensor, new Date()));

        doReturn(sensorList).when(this.sensorRepository).findByName(measurement.getSensor().getName());

        //when
        this.measurementService.add(measurement);

        //then
        verify(measurementRepository).save(measurement);
        verifyNoMoreInteractions(sensorRepository);
        verifyNoMoreInteractions(measurementRepository);

    }

    @Test
    void add_NotExistingSensor_ThrowsException(){
        //given
        List<Sensor> sensorList = new ArrayList<>();
        Sensor  sensor = new Sensor(1, "Sensor 1", List.of());
        sensorList.add(sensor);
        Measurement measurement = (new Measurement(1, BigDecimal.valueOf(50), true, sensor, new Date()));

        doReturn(Collections.EMPTY_LIST).when(this.sensorRepository).findByName(measurement.getSensor().getName());

        //when
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, ()->this.measurementService.add(measurement));

        //then
        verifyNoMoreInteractions(sensorRepository);
        verifyNoMoreInteractions(measurementRepository);
        assertTrue(thrown.getMessage().contains("Sensor with such name does not exist"));


    }



}
