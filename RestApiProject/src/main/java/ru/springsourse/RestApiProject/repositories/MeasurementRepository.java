package ru.springsourse.RestApiProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springsourse.RestApiProject.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,  Integer> {
    List<Measurement> findAll();

    List<Measurement> findByRaining(Boolean value);


}
