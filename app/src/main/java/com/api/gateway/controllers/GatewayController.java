package com.api.gateway.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GatewayController {

  @RequestMapping(value = {"/v1/app/**"}, method = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.DELETE})
  public String gatewayMap(HttpServletRequest req){

  }
}
