package ru.springsourse.RestApiProject.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.springsourse.RestApiProject.dto.MeasurementDTO;
import ru.springsourse.RestApiProject.models.Measurement;
import ru.springsourse.RestApiProject.services.MeasurementService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    @PostMapping("/add")
    public ResponseEntity addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors()
                            .stream().map(x->x.getDefaultMessage()).reduce("", (x, y) -> (x + "; " + y)));

        try {
            measurementService.add(modelMapper.map(measurementDTO, Measurement.class));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDTO> getAll(){
        return measurementService.findAll()
                .stream().map(x->modelMapper.map(x, MeasurementDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyCount(){
        return measurementService.countRainy();
    }
}
