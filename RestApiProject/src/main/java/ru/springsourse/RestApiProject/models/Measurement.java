package ru.springsourse.RestApiProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "measurement", schema = "sensor")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "temperature")
    @NotEmpty(message = "Temperature cannot be empty")
    @DecimalMin(value = "-100.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    private BigDecimal temperature;

    @Column(name = "raining")
    @NotEmpty(message = "Raining field cannot be empty")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "measure_time")
    @Temporal(TemporalType.TIMESTAMP)
    @NotEmpty
    private Date measuretime;

}
