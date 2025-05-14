package com.api.gateway.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.api.gateway.Util;
import com.api.gateway.models.ApiModel;
import com.api.gateway.models.AuthRequest;
import com.api.gateway.models.AuthResponse;
import com.api.gateway.reader.ConfigDefinition;
import com.api.gateway.security.JwsUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class AuthTest {
  
  @Mock
  private RestTemplate authRestTemplate;

  @Mock
  private Util util;

  @Mock
  private ConfigDefinition configDef;

  @Mock
  private JwsUtil jwsUtil;

  @InjectMocks
  private Auth controller;

  @Test
  public void testLogin() throws JsonProcessingException, RestClientException, AuthException{
    AuthRequest loginForm = new AuthRequest();
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    String targetUrl = "/target";
    ApiModel api1 = new ApiModel(GatewayController.PREFIX + "login", targetUrl, false);
    List<ApiModel> apis = new ArrayList<>();
    @SuppressWarnings("unchecked")
    ResponseEntity<String> mockResponse = Mockito.mock(ResponseEntity.class);
    HttpStatusCode statusCode = HttpStatus.OK;
    apis.add(api1);

    Mockito.when(configDef.getApis()).thenReturn(apis);
    Mockito.when(mockResponse.getStatusCode()).thenReturn(statusCode);
    Mockito.when(util.getObjectAsJson(loginForm)).thenReturn("mock json");
    Mockito.when(authRestTemplate.postForEntity(Mockito.eq(targetUrl), Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenReturn(mockResponse);
    AuthResponse out = controller.login(loginForm,response);

    assertEquals(AuthResponse.Status.OK,out.getStatus());
  }

  @Test
  public void testLogin_fail() throws JsonProcessingException, RestClientException, AuthException{
    AuthRequest loginForm = new AuthRequest();
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    String targetUrl = "/target";
    ApiModel api1 = new ApiModel(GatewayController.PREFIX + "login", targetUrl, false);
    List<ApiModel> apis = new ArrayList<>();
    @SuppressWarnings("unchecked")
    ResponseEntity<String> mockResponse = Mockito.mock(ResponseEntity.class);
    HttpStatusCode statusCode = HttpStatus.FORBIDDEN;
    apis.add(api1);

    Mockito.when(configDef.getApis()).thenReturn(apis);
    Mockito.when(mockResponse.getStatusCode()).thenReturn(statusCode);
    Mockito.when(util.getObjectAsJson(loginForm)).thenReturn("mock json");
    Mockito.when(authRestTemplate.postForEntity(Mockito.eq(targetUrl), Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenReturn(mockResponse);
    AuthResponse out = controller.login(loginForm,response);

    assertNotEquals(AuthResponse.Status.OK,out.getStatus());
  }
  
}
