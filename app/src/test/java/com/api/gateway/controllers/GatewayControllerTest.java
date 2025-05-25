package com.api.gateway.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.api.gateway.exceptions.GatewayException;
import com.api.gateway.models.ApiModel;
import com.api.gateway.reader.ConfigDefinition;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class GatewayControllerTest {

  @Mock
  private ConfigDefinition configDef;

  @InjectMocks
  private GatewayController controller;

  @Test
  public void testGatewayMap() throws GatewayException, IOException{
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    // mock how the ConfigDefinition initializes api models
    ApiModel api1 = new ApiModel(GatewayController.PREFIX +"test", "http://localhost:8081/test", false);
    ApiModel api2 = new ApiModel(GatewayController.PREFIX +"test2", "http://localhost:8081/test2", false);
    ApiModel api3 = new ApiModel(GatewayController.PREFIX +"test3", "http://localhost:8081/test3", false);
    List<ApiModel> apiList = new ArrayList<>();
    Vector<String>  vector = new Vector<>();
    String header1key = "content";
    String header2key = "Authorization";
    String header1Val = "JSON";
    String header2Val = "fasdfafas";
    URI mockUri = Mockito.mock(URI.class);
    URL mockUrl = Mockito.mock(URL.class);
    BufferedReader reader = Mockito.mock(BufferedReader.class);
    HttpURLConnection mockHttpCon = Mockito.mock(HttpURLConnection.class);
    String reqMethod = "GET";
    OutputStream mockOutputStream = Mockito.mock(OutputStream.class);
    InputStream mockInputStream = Mockito.mock(InputStream.class);
    Map<String,List<String>> headerMap = new HashMap<>();

    headerMap.put(header1key, Arrays.asList(header1Val));
    vector.add(header1key);
    vector.add(header2key);
    apiList.add(api1);
    apiList.add(api2);
    apiList.add(api3);
    Mockito.when(req.getRequestURI()).thenReturn(api1.getSrcPath());
    Mockito.when(req.getMethod()).thenReturn(reqMethod);
    Mockito.when(configDef.getApis()).thenReturn(apiList);
    Mockito.when(req.getHeaderNames()).thenReturn(vector.elements());
    Mockito.when(req.getHeader(header1key)).thenReturn(header1Val);
    Mockito.when(req.getReader()).thenReturn(reader);
    Mockito.when(mockUri.toURL()).thenReturn(mockUrl);
    Mockito.when(mockUrl.openConnection()).thenReturn(mockHttpCon);
    Mockito.when(mockHttpCon.getOutputStream()).thenReturn(mockOutputStream);
    Mockito.when(mockHttpCon.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
    Mockito.when(mockHttpCon.getInputStream()).thenReturn(mockInputStream);
    Mockito.when(mockHttpCon.getHeaderFields()).thenReturn(headerMap);

    try(MockedStatic<URI> mockUriStatic = Mockito.mockStatic(URI.class)){
      mockUriStatic.when(() -> URI.create(api1.getTarget())).thenReturn(mockUri);
      controller.gatewayMap(req,response);
    }
    Mockito.verify(mockHttpCon).setDoOutput(true);
    Mockito.verify(mockHttpCon).setRequestMethod(reqMethod);
    Mockito.verify(mockHttpCon).setRequestProperty(header1key, header1Val);
    Mockito.verify(mockHttpCon,Mockito.never()).setRequestProperty(header2key, header2Val);
    Mockito.verify(response).addHeader(header1key,header1Val);
  }

}
