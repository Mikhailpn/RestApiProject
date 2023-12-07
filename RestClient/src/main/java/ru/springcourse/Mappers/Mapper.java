package ru.springcourse.Mappers;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface  Mapper <I, R> {
    public R map(I input) throws JsonProcessingException;
}
