package com.api.gateway.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class WebSecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
      .authorizeHttpRequests(
          (req) -> req.requestMatchers("/").permitAll().anyRequest().authenticated()
      ).formLogin((form) -> form.loginPage("/login").permitAll()).logout((logout) -> logout.permitAll());
    return http.build();
  }

}
