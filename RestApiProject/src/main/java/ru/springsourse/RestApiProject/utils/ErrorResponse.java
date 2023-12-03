package ru.springsourse.RestApiProject.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    public String message;

    public Long millis;


}
