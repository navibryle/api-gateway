package com.api.gateway.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.gateway.controllers.GatewayController;
import com.api.gateway.models.ApiModel;
import com.api.gateway.reader.ConfigDefinition;

@Configuration
public class MockGatewayContext {
  
  @Bean
  public ConfigDefinition configDefinition(){
    ApiModel api1 = new ApiModel(GatewayController.PREFIX +"test", "http://localhost:8081/test", false);
    ApiModel api2 = new ApiModel(GatewayController.PREFIX +"test2", "http://localhost:8081/test2", false);
    ApiModel api3 = new ApiModel(GatewayController.PREFIX +"test3", "http://localhost:8081/test3", false);
    List<ApiModel> apiList = new ArrayList<>();

    apiList.add(api1);
    apiList.add(api2);
    apiList.add(api3);

    ConfigDefinition configDefinition = new ConfigDefinition();
    configDefinition.setApis(apiList);

    return configDefinition;
  }

  @Bean
  public GatewayController gatewayController(){
    return new GatewayController();
  }
}
