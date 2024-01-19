package ru.springsourse.RestApiProject.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.repositories.SensorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SensorTest extends ContainerTest {

    @Autowired
    MockMvc mockMvc;


    @Autowired
    SensorRepository sensorRepository;

    @Test
    public void performRegistration_notExistingSensor_returnsOkAndSavesSensor() throws Exception {
        //given

        //when
        RequestBuilder request = post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Sensor 1"}
                        """);

        mockMvc.perform(request)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));

        List<Sensor> sensorsDB = sensorRepository.findByName("Sensor 1");
        assertEquals(sensorsDB.size(), 1);
    }

    @Test
    public void performRegistration_existingSensor_returnsConflictAndNotSaveSensor() throws Exception {
        //given
        Sensor sensor = new Sensor();
        sensor.setName("Sensor 1");
        sensor.setMeasurementList(Collections.emptyList());
        sensorRepository.save(sensor);
        List<Integer> list = new LinkedList<>();



        //when
        RequestBuilder request = post("/sensors/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Sensor 1"}
                        """);

        mockMvc.perform(request)
                //then
                .andExpectAll(status().isConflict(),
                        status().reason((containsString("Sensor with name Sensor 1 already exists"))));

        assertEquals(1, sensorRepository.findByName("Sensor 1").size());

    }
}

