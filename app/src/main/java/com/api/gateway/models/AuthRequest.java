package com.api.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequest {
  @JsonProperty(required = true)
  private String username;
  @JsonProperty(required = true)
  private String password;
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
}
