package ru.springcourse.Generators;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import ru.springcourse.JsonObjects.Sensor;

@Component
public class SensorGenerator {
    public Sensor generate() {
        String name = RandomStringUtils.randomAlphanumeric(10);
        //TODO random name
        return new Sensor(name);
    }

}
