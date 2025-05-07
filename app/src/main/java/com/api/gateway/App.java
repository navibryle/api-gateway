package com.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class App {

  @RequestMapping("/test")
  public String test(){
    throw new RuntimeException();
  }

  @RequestMapping("/error")
  public String error(){
    return "REDIRECTED TO ERROR";
  }

  @RequestMapping("/login")
  public String login(){
    return "login";
  }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
