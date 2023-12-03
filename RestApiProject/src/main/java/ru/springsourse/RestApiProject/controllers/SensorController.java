package ru.springsourse.RestApiProject.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.dto.SensorDTO;
import ru.springsourse.RestApiProject.models.Sensor;
import ru.springsourse.RestApiProject.services.SensorService;
import java.lang.*;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity performRegistration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult
                            .getAllErrors().stream()
                            .map(x->x.getDefaultMessage()).reduce(";", String::concat));

        sensorService.save(modelMapper.map(sensorDTO, Sensor.class));

        return ResponseEntity.ok(HttpStatus.OK);

    }




}
