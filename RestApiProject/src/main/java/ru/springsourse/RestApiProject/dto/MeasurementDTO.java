package ru.springsourse.RestApiProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.springsourse.RestApiProject.models.Sensor;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MeasurementDTO {



    @DecimalMin(value = "-100.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    private BigDecimal temperature;

    @NotNull
    private Boolean raining;
    @NotNull
    private Sensor sensor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date measuretime;
}
