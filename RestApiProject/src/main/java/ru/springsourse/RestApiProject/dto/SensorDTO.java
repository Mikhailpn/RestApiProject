package ru.springsourse.RestApiProject.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.springsourse.RestApiProject.models.Measurement;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDTO {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

}
