package com.api.gateway.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.api.gateway.models.ApiModel;

import jakarta.annotation.PostConstruct;

@Component
public class ConfigDefinition {

  public List<ApiModel> apis;

  public List<ApiModel> getApis() {
    return apis;
  }

  public void setApis(List<ApiModel> apis) {
    this.apis = apis;
  }

  @PostConstruct
  private void init(){
    // ADD ENDPOINTS HERE
    apis = new ArrayList<>();
    // src,target
    apis.add(new ApiModel("/login","http://localhost:8080/login",false));
  }
}
