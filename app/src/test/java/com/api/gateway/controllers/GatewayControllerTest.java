package com.api.gateway.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.api.gateway.models.ApiModel;
import com.api.gateway.reader.ConfigDefinition;

import jakarta.servlet.http.HttpServletRequest;

@RunWith(MockitoJUnitRunner.class)
public class GatewayControllerTest {

  @Mock
  private ConfigDefinition configDef;

  @InjectMocks
  private GatewayController controller;

  @Test
  public void testGatewayMap(){
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    // mock how the ConfigDefinition initializes api models
    ApiModel api1 = new ApiModel(GatewayController.PREFIX +"test", "http://localhost:8081/test", false);
    ApiModel api2 = new ApiModel(GatewayController.PREFIX +"test2", "http://localhost:8081/test2", false);
    ApiModel api3 = new ApiModel(GatewayController.PREFIX +"test3", "http://localhost:8081/test3", false);
    List<ApiModel> apiList = new ArrayList<>();
    apiList.add(api1);
    apiList.add(api2);
    apiList.add(api3);
    Mockito.when(req.getRequestURI()).thenReturn(api1.getSrcPath());
    Mockito.when(configDef.getApis()).thenReturn(apiList);

    assertEquals(api1.getTarget(),controller.gatewayMap(req));
  }

  
}
