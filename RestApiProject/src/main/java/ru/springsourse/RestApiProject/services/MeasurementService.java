package ru.springsourse.RestApiProject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.models.Measurement;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.repositories.MeasurementRepository;
import ru.springsourse.RestApiProject.repositories.SensorRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    @Transactional
    public void add(Measurement measurement){
        Optional<Sensor> sensor = sensorRepository.findByName(measurement.getSensor().getName()).stream().findAny();
        if(sensor.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor with such name does not exist");

        measurement.setSensor(sensor.get());
        measurement.setMeasuretime(new Date());
        measurementRepository.save(measurement);
    }

    @Transactional(readOnly = true)
    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public int countRainy(){
        return measurementRepository.findByRaining(true).size();
    }
}
