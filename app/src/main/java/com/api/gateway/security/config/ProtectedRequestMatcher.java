package com.api.gateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.api.gateway.reader.ConfigDefinition;
import com.api.gateway.security.JwsUtil;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProtectedRequestMatcher implements RequestMatcher{

  @Autowired
  private ConfigDefinition configDef;

  @Autowired
  private JwsUtil jwsUtil;

  @Override
  public boolean matches(HttpServletRequest request) {
    return configDef.getApis().stream().filter((api) -> {
      boolean pathMatch = request.getRequestURI().toLowerCase().equals(api.getSrcPath());
      if (pathMatch && api.isProtected()){
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(bearerToken)){
          return jwsUtil.isJwsValid(bearerToken);
        }
        return false;
      }
      return pathMatch;
    }).toList().size() > 0;
  }

}
