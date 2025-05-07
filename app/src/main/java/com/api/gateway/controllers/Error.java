package com.api.gateway.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Error {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(Exception.class)
  public String handle404(){
    // This is returned when there is an error
    // TODO: replace this
    return "Error";
  }
}
