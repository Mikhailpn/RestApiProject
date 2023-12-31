package ru.springcourse;

import ru.springcourse.JsonObjects.Measurement;
import ru.springcourse.JsonObjects.Sensor;
import ru.springcourse.Mappers.Mapper;
import ru.springcourse.WebUtils.WebClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiClient {

    private final WebClientRequest webClientRequest;

    private final Mapper mapper;

    public String addSensor(Sensor sensor){
        String response;
        try {
            response = webClientRequest.makePostRequest("/sensors/registration", sensor);
        }catch (IOException e){
            response = e.getMessage();
        }

        System.out.println(response);
        return response;
    }

    public String addMeasurement(Measurement measurement){
        String response;
        try {
            response = webClientRequest.makePostRequest("/measurements/add", measurement);
        }catch (IOException e){
            response = e.getMessage();
        }

        System.out.println(response);
        return response;
    }

    public List<Measurement> getMeasurements() throws IOException {
        return (List<Measurement>)mapper.map(webClientRequest.makeGetRequest("/measurements"));
    }
}
