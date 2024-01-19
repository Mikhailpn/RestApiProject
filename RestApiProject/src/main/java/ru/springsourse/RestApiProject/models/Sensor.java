package ru.springsourse.RestApiProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "sensor", schema = "sensor")
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @JsonProperty("name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sensor")
    @JsonIgnore
    List<Measurement> measurementList;

}
