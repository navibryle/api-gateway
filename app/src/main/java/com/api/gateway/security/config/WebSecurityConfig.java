package com.api.gateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  @Autowired
  ProtectedRequestMatcher protectedRequestMatcher;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
      .authorizeHttpRequests(
          (req) -> req.requestMatchers(protectedRequestMatcher).permitAll().anyRequest().authenticated()
     );
    return http.build();
  }
}
