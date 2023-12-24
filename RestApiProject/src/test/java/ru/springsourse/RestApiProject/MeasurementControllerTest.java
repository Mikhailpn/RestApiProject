package ru.springsourse.RestApiProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.controllers.MeasurementController;
import ru.springsourse.RestApiProject.dto.MeasurementDTO;
import ru.springsourse.RestApiProject.dto.SensorDTO;
import ru.springsourse.RestApiProject.models.Measurement;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.services.MeasurementService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementControllerTest {

    @Mock
    ModelMapper modelMapper;

    @Mock
    MeasurementService measurementService;

    @InjectMocks
    MeasurementController measurementController;

    @Test
    void addMeasurement_validMeasurement_returnsResponseEntityOK(){
        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setRaining(true);
        measurementDTO.setTemperature(BigDecimal.valueOf(10));
        measurementDTO.setSensor(sensor);

        Measurement measurement = new Measurement();
        measurement.setSensor(sensor);
        measurement.setTemperature(BigDecimal.valueOf(10));
        measurement.setRaining(true);

        doReturn(measurement).when(modelMapper).map(measurementDTO, Measurement.class);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        //when
        ResponseEntity response = this.measurementController.addMeasurement(measurementDTO, bindingResult);

        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(measurementService).add(measurement);
    }

    @Test
    void addMeasurement_validMeasurement_throwsResponseStatusException(){
        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setRaining(true);
        measurementDTO.setTemperature(BigDecimal.valueOf(10));
        measurementDTO.setSensor(sensor);


        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        //when
        assertThrows(ResponseStatusException.class, ()->this.measurementController.addMeasurement(measurementDTO, bindingResult));

        //then
        verifyNoInteractions(measurementService);
    }


    /*
    @Test
    void getAll_ReturnsNotEmptyListOfMeasurements(){
        //given
        Sensor sensor = new Sensor(1, "Sensor 1", List.of());
        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(new Measurement(1, BigDecimal.valueOf(50), true, sensor, new Date()));
        measurementList.add(new Measurement(2, BigDecimal.valueOf(40), false, sensor, new Date()));
        sensor.setMeasurementList(measurementList);

        List<MeasurementDTO> measurementDTOList = new ArrayList<>();
        measurementList.forEach(x->measurementDTOList.add(new MeasurementDTO(x.getTemperature(), x.getRaining(), x.getSensor(), x.getMeasuretime())));

        doReturn(measurementList).when(this.measurementService).findAll();
        doReturn().when(modelMapper).map(m)

        //when
        var resMeasurementDTOList = this.measurementService.findAll();

        //then
        assertNotNull(resMeasurementList);
        assertEquals(resMeasurementList.size(), 2);
        assertEquals(resMeasurementDTOList, measurementList);

    }
    */
     
}
