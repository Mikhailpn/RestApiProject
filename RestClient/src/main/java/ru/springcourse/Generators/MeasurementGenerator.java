package ru.springcourse.Generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Component;
import ru.springcourse.JsonObjects.Measurement;
import ru.springcourse.JsonObjects.Sensor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.random.RandomGenerator;

@Component
public class MeasurementGenerator {
    public Measurement generate(Sensor sensor){
        BigDecimal temperature = BigDecimal.valueOf(new Random().nextDouble(40, 60));
        Boolean raining = new Random().nextBoolean();

        return new Measurement(temperature, raining, new Date(), sensor);
    }
}
