import JsonObjects.Measurement;
import JsonObjects.Sensor;
import Mappers.JsonToMeasurementList;
import Mappers.Mapper;
import WebUtils.WebClientRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApiClient {
    public String addSensor(Sensor sensor){
        String response;
        try {
            WebClientRequest webClientRequest = new WebClientRequest();
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
            WebClientRequest webClientRequest = new WebClientRequest();
            response = webClientRequest.makePostRequest("/measurements/add", measurement);
        }catch (IOException e){
            response = e.getMessage();
        }

        System.out.println(response);
        return response;
    }

    public List<Measurement> getMeasurements() throws IOException {
        WebClientRequest webClientRequest = new WebClientRequest();
        Mapper mapper= new JsonToMeasurementList();
        return (List<Measurement>)mapper.map(webClientRequest.makeGetRequest("/measurements"));
    }
}
