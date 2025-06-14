package com.api.gateway.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.api.gateway.controllers.GatewayController;
import com.api.gateway.models.ApiModel;

import jakarta.annotation.PostConstruct;

@Component
public class ConfigDefinition {

  private List<ApiModel> apis;

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
    // src must not start with /, it will be added by GatewayController.PREFIX
    // THE FOLLOWING IS EXAMPLE CODE
    // FOLLOW THE EXAMPLE TO ADD MORE APIS
    apis.add(new ApiModel("login","http://localhost:8081/user",false)); // Special endpoint for getting JWT token for secured endpoints
    apis.add(new ApiModel("register","http://localhost:8081/register",false)); // Special endpoint for registering the user
    apis.add(new ApiModel("error","http://localhost:8081/error",false));
    apis.add(new ApiModel("xd","http://localhost:8081/error",false));
    apis.add(new ApiModel("1","http://localhost:8081/error",false));
    apis.add(new ApiModel("2","http://localhost:8081/error",false));
    apis.add(new ApiModel("3","http://localhost:8081/error",false));
    // EXAMPLE CODE ENDS HERE

    apis.forEach((api) -> api.setSrcPath(GatewayController.PREFIX+api.getSrcPath()));
  }
}
