package ru.springsourse.RestApiProject.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.springsourse.RestApiProject.models.Sensor;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MeasurementDTO {


    @NotEmpty(message = "Temperature cannot be empty")
    @DecimalMin(value = "-100.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    private BigDecimal temperature;


    @NotEmpty(message = "Raining field cannot be empty")
    private boolean raining;

    private Sensor sensor;


    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    private Date measuretime;
}
