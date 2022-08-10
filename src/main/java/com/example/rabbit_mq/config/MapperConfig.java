package com.example.rabbit_mq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
