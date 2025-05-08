package com.api.gateway.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.api.gateway.Util;
import com.api.gateway.models.AuthRequest;
import com.api.gateway.models.AuthResponse;
import com.api.gateway.models.RegisterRequest;
import com.api.gateway.reader.ConfigDefinition;
import com.api.gateway.security.JwsUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;

@Controller("/auth")
public class Auth {

  @Autowired
  RestTemplate authRestTemplate;

  @Autowired
  private Util util;

  @Autowired
  private JwsUtil jwsUtil;

  @Autowired
  private ConfigDefinition configDef;

  private String getLoginUrlForApi() throws AuthException{
    List<String> authUrls = configDef.getApis().stream().map((api) -> api.getSrcPath()).filter((srcPath) -> srcPath.equals("/login")).toList();
    if (authUrls.size() != 1){
      throw new AuthException("Incorrect api gateway configuration");
    }
    return authUrls.get(0);
  }

  private String getRegistrationUrlForApi() throws AuthException{
    List<String> authUrls = configDef.getApis().stream().map((api) -> api.getSrcPath()).filter((srcPath) -> srcPath.equals("/registration")).toList();
    if (authUrls.size() != 1){
      throw new AuthException("Incorrect api gateway configuration");
    }
    return authUrls.get(0);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest loginForm, HttpServletResponse response) throws RestClientException, AuthException, JsonProcessingException{
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> httpEntity = new HttpEntity<String>(util.getObjectAsJson(loginForm),headers);
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<String> authResponse = authRestTemplate.postForEntity(getLoginUrlForApi(), httpEntity,String.class);
    AuthResponse out = new AuthResponse();
    if (authResponse.getStatusCode().is2xxSuccessful()){
      out.setBearerToken(jwsUtil.getJws(loginForm.getUsername()));
      out.setStatus(AuthResponse.Status.OK);
    }else{
      out.setStatus(AuthResponse.Status.NOT_OK);
    }
    return out;
  }

  @PostMapping("/register")
  public AuthResponse register(@RequestBody RegisterRequest registrationForm,HttpServletResponse response) throws RestClientException, AuthException, JsonProcessingException{
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> httpEntity = new HttpEntity<String>(util.getObjectAsJson(registrationForm),headers);
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<String> authResponse = authRestTemplate.postForEntity(getRegistrationUrlForApi(), httpEntity,String.class);
    AuthResponse out = new AuthResponse();
    if (authResponse.getStatusCode().is2xxSuccessful()){
      out.setBearerToken(jwsUtil.getJws(registrationForm.getUsername()));
      out.setStatus(AuthResponse.Status.OK);
    }else{
      out.setStatus(AuthResponse.Status.NOT_OK);
    }
    return out;
  }
}
