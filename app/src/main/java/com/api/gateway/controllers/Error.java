package com.api.gateway.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class Error {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public String handle404(){
    // This is returned when there is an error
    // TODO: replace this
    return "Not found";
  }
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  public String handle403(){
    // This is returned when there is an error
    // TODO: replace this
    return "forbidden";
  }
}
