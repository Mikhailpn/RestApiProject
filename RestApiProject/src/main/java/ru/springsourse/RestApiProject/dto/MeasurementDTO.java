package ru.springsourse.RestApiProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.springsourse.RestApiProject.models.Sensor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {

    @JsonProperty("temperature")
    @DecimalMin(value = "-100.0", inclusive = true, message = "Temperature must be > -100")
    @DecimalMax(value = "100.0", inclusive = true, message = "Temperature must be < 100")
    private BigDecimal temperature;

    @JsonProperty("raining")
    @NotNull(message = "Raining must be true or false, not null")
    private Boolean raining;
    @JsonProperty("sensor")
    @NotNull
    private Sensor sensor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date measuretime;
}
