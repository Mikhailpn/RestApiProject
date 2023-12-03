package ru.springsourse.RestApiProject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.repositories.SensorRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;

    @Transactional
    public void save (Sensor sensorFromRequest){
        Optional<Sensor> sensorFromDB = sensorRepository.findByName(sensorFromRequest.getName()).stream().findAny();
        if (sensorFromDB.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sensor with name " + sensorFromRequest.getName() +  " already exists");
        sensorRepository.save(sensorFromRequest);
    }
}
