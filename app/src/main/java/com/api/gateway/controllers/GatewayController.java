package com.api.gateway.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.gateway.models.ApiModel;
import com.api.gateway.reader.ConfigDefinition;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GatewayController {

  public static final String PREFIX = "/v1/app/";
  private static final String PREFIX_MATCHER = "/v1/app/**";

  @Autowired
  private ConfigDefinition configDef;

  @RequestMapping(value = {GatewayController.PREFIX_MATCHER}, method = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.DELETE})
  @ResponseBody
  public String gatewayMap(HttpServletRequest req){
    String srcUrl = new AntPathMatcher().extractPathWithinPattern(GatewayController.PREFIX_MATCHER,req.getRequestURI());

    List<ApiModel> matchingApis = configDef.getApis().stream().filter((api) -> 
      api.getSrcPath().trim().toLowerCase().equals(GatewayController.PREFIX + srcUrl.trim())
    ).toList();

    if(matchingApis.size() > 1 || matchingApis.size() == 0){
      throw new RuntimeException("This should not be possible since there is a check for this in the filter chain");
    }
    return matchingApis.get(0).getTarget();
  }
}
