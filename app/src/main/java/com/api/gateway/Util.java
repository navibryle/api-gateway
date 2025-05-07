package com.api.gateway;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Util {

  /**
   * @param obj object to turn to json
   * @return json object as string
   * @throws JsonProcessingException 
   */
  public String getObjectAsJson(Object obj) throws JsonProcessingException{
    ObjectMapper objMapper = new ObjectMapper();
    return objMapper.writeValueAsString(obj);
  }
}
