package ru.springsourse.RestApiProject.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.annotations.Cascade;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.dto.MeasurementDTO;
import ru.springsourse.RestApiProject.models.Measurement;
import ru.springsourse.RestApiProject.services.MeasurementService;

import java.lang.reflect.RecordComponent;

@Component
@RequiredArgsConstructor
public class SensorKafkaConsumer {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;
    @KafkaListener(topics = "topic1", groupId = "test_group")
    public void onMessage(ConsumerRecord<String, String> message) throws JsonProcessingException {
            System.out.println(message.value());
            MeasurementDTO measurementDTO = objectMapper.readValue(message.value(), MeasurementDTO.class);
            try {
                measurementService.add(modelMapper.map(measurementDTO, Measurement.class));
            }catch (Exception e){
                System.out.println(e.getStackTrace());
            }
    }

}
