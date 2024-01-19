package ru.springcourse.Mappers;

import ru.springcourse.JsonObjects.Measurement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonToMeasurementList implements Mapper<String, List<Measurement>>{

    @Override
    public List<Measurement> map(String input) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Measurement>> listType = new TypeReference<List<Measurement>>() {};
        return objectMapper.readValue(input, listType);
    }

    public void test(){
        System.out.println("lalallal");
    }

}
