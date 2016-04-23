package com.origins.osvik.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
@Configuration
@ComponentScan({"com.origins.osvik.web.rest", "com.origins.osvik.web.rest.exception"})
@EnableAsync
public class ApiDispatcherServletConfiguration extends WebMvcConfigurationSupport {
    private final Logger log = LoggerFactory.getLogger(ApiDispatcherServletConfiguration.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public SessionLocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
        return requestMappingHandlerMapping;
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        addDefaultHttpMessageConverters(converters);
        for (HttpMessageConverter<?> converter : converters) {
            if ((converter instanceof MappingJackson2HttpMessageConverter)) {
                MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converter;
                jackson2HttpMessageConverter.setObjectMapper(this.objectMapper);
                break;
            }
        }
    }
}
