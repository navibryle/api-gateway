package com.api.gateway.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ControllerContext {
  @Bean
  public RestTemplate authRestTemplate(){
    return new RestTemplate();
  }
}
