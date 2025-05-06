package com.api.gateway.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Error {

  @GetMapping("/error")
  public String err(){
    return "ERROR";
  }
}
