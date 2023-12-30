package ru.springsourse.RestApiProject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ru.springsourse.RestApiProject.models.Measurement;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.repositories.MeasurementRepository;
import ru.springsourse.RestApiProject.repositories.SensorRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MeasurementTest extends ContainerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    SensorRepository sensorRepository;

    @Test
    void getAll_returnsCorrectResponse() throws Exception {

        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");

        Measurement measurement1 = new Measurement(1, BigDecimal.valueOf(50), true, sensor, new Date());
        Measurement measurement2 = new Measurement(2, BigDecimal.valueOf(60), false, sensor, new Date());
        List<Measurement> measurementList = List.of(measurement1, measurement2);

        sensor.setMeasurementList(measurementList);

        sensorRepository.save(sensor);
        measurementRepository.save(measurement1);
        measurementRepository.save(measurement2);

        //when
        mockMvc.perform(get("/measurements"))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    [
                                    {
                                     "temperature":50,
                                     "raining":true,
                                     "sensor":{"name":"Sensor 1"}
                                    },
                                    {
                                     "temperature":60,
                                     "raining":false,
                                     "sensor":{"name":"Sensor 1"}
                                     }]
                                        """)
                );

    }
    @Test
    void getRainyCount_returnsCorrectRainyCount() throws Exception{
        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");

        Measurement measurement1 = new Measurement(1, BigDecimal.valueOf(50), true, sensor, new Date());
        Measurement measurement2 = new Measurement(2, BigDecimal.valueOf(60), false, sensor, new Date());
        List<Measurement> measurementList = List.of(measurement1, measurement2);

        sensor.setMeasurementList(measurementList);

        sensorRepository.save(sensor);
        measurementRepository.save(measurement1);
        measurementRepository.save(measurement2);

        //when
        mockMvc.perform(get("/measurements/rainyDaysCount"))
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("1")
                );

    }

    @Test
    @DisplayName("валидный measurement, в БД есть sensor, ожидается OK и сохранение в БД")
    public void addMeasurement_existingSensorValidMeasurement_returnsOkAndSavesMeasurement() throws Exception {
        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");
        sensor.setMeasurementList(Collections.emptyList());
        sensorRepository.save(sensor);

        //when
        RequestBuilder request = post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "temperature":10.7,
                            "raining":true,
                            "sensor":{"name": "Sensor 1"}           
                        }
                        """);

        mockMvc.perform(request)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));


        List<Measurement> measurementList = measurementRepository.findAll();
        assertEquals(1, measurementList.size());

        Measurement measurement = measurementList.get(0);

        assertEquals(BigDecimal.valueOf(10.7), measurement.getTemperature());
        assertEquals(true, measurement.getRaining());
        assertNotNull(measurement.getMeasuretime());
        assertEquals("Sensor 1", measurement.getSensor().getName());
    }

    @Test
    @DisplayName("валидный measurement, в БД нет sensor, ожидается ошибка NOT_FOUND и нет сохранения в БД")
    public void addMeasurement_notExistingSensorValidMeasurement_returnsNotFoundAndNotSaveMeasurement() throws Exception {
        //given

        //when
        RequestBuilder request = post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "temperature":10.7,
                            "raining":true,
                            "sensor":{"name": "Sensor 1"}           
                        }
                        """);

        mockMvc.perform(request)
                //then
                .andExpectAll(status().isNotFound(),
                        status().reason(containsString("Sensor with such name does not exist")));

        List<Measurement> measurementList = measurementRepository.findAll();
        assertEquals(0, measurementList.size());
    }

    @Test
    @DisplayName("невалидный measurement (temperature > max), в БД есть sensor, ожидается ошибка 400 и нет сохранения в БД")
    public void addMeasurement_existingSensorInvalidMeasurementTemp_returnsNotFoundAndNotSaveMeasurement() throws Exception {
        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");
        sensor.setMeasurementList(Collections.emptyList());
        sensorRepository.save(sensor);

        //when
        RequestBuilder request = post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "temperature":100.7,
                            "raining":true,
                            "sensor":{"name": "Sensor 1"}           
                        }
                        """);

        mockMvc.perform(request)
                //then
                .andExpectAll(status().is4xxClientError(),
                        status().reason(containsString("Temperature must be < 100")));

        List<Measurement> measurementList = measurementRepository.findAll();
        assertEquals(0, measurementList.size());
    }

    @Test
    @DisplayName("невалидный measurement (raining empty), в БД есть sensor, ожидается ошибка 400 и нет сохранения в БД")
    public void addMeasurement_existingSensorInvalidMeasurementRaining_returnsNotFoundAndNotSaveMeasurement() throws Exception {
        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");
        sensor.setMeasurementList(Collections.emptyList());
        sensorRepository.save(sensor);

        //when
        RequestBuilder request = post("/measurements/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "temperature":10.7,
                            "raining":"",
                            "sensor":{"name": "Sensor 1"}           
                        }
                        """);

        mockMvc.perform(request)
                //then
                .andExpectAll(status().is4xxClientError(),
                        status().reason(containsString("Raining must be true or false, not null")));

        List<Measurement> measurementList = measurementRepository.findAll();
        assertEquals(0, measurementList.size());
    }


}
