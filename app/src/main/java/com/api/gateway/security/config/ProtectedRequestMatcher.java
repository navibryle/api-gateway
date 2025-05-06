package com.api.gateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.api.gateway.reader.ConfigDefinition;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProtectedRequestMatcher implements RequestMatcher{

  @Autowired
  ConfigDefinition configDef;

  @Override
  public boolean matches(HttpServletRequest request) {
    return configDef.getApis().stream().filter((api) -> {
      System.out.println("DEBUGPRINT[55]: ProtectedRequestMatcher.java:19 (after return configDef.getApis().stream().filt…)");
      System.out.println(request.getRequestURI());
      System.out.println(request.getLocalName());
      System.out.println(request.getPathInfo());
      System.out.println(request.getLocalAddr());
      System.out.println("DEBUGPRINT[56]: ProtectedRequestMatcher.java:20 (after System.err.println(DEBUGPRINT[55]: Prote…)");

      return request.getRequestURI().toLowerCase().equals(api.getSrcPath());
    }).toList().size() > 0;
  }

}
