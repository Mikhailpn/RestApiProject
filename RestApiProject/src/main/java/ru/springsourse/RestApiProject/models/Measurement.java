package ru.springsourse.RestApiProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "measurement", schema = "sensor")
@AllArgsConstructor
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "temperature")
    @NotNull
    @DecimalMin(value = "-100.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    private BigDecimal temperature;

    @Column(name = "raining")
    @NotNull
    private Boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "measure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date measuretime;

}
