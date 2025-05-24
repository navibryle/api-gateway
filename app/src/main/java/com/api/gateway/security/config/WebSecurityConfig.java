package com.api.gateway.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.api.gateway.exceptions.GatewayException;
import com.api.gateway.exceptions.GatewayException.MSG;
import com.api.gateway.reader.ConfigDefinition;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  @Autowired
  ProtectedRequestMatcher protectedRequestMatcher;

  @Autowired
  ConfigDefinition configDefinition;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    List<String> registerEndpoint = configDefinition.getApis().stream().filter((api) -> api.getSrcPath().toLowerCase().contains("register")).map((api) -> api.getSrcPath()).toList();
    if (registerEndpoint.size() == 0){
      http
        .authorizeHttpRequests(
            (req) -> req.requestMatchers(protectedRequestMatcher).permitAll().anyRequest().authenticated()
       );
    }else if(registerEndpoint.size() == 1){
      http
        .csrf((csrf) -> csrf.ignoringRequestMatchers(registerEndpoint.get(0)))
        .authorizeHttpRequests(
            (req) -> req.requestMatchers(protectedRequestMatcher).permitAll().anyRequest().authenticated()
       );
    }else{
      throw new GatewayException(MSG.MULTIPLE_REGISTER);
    }
    return http.build();
  }
}
