package ru.springsourse.RestApiProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("name")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

}
