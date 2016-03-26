package com.origins.osvik.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
@Configuration
public class JacksonConfiguration {
    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }
}
