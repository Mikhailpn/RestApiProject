package Mappers;

import JsonObjects.Measurement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonToMeasurementList implements Mapper<String, List<Measurement>>{

    @Override
    public List<Measurement> map(String input) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Measurement>> listType = new TypeReference<List<Measurement>>() {};
        return objectMapper.readValue(input, listType);
    }
}
