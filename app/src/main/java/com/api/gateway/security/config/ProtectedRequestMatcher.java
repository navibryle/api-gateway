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
    configDef.getApis().stream().forEach((api) -> {
      request.getRequestURI().matches(api.getSrcPath());
    });
  }

}
