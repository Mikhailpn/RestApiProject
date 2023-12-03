package ru.springsourse.RestApiProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApiConfig {
    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
