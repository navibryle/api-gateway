package com.api.gateway.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
  @JsonProperty(required = true)
  private String username;
  @JsonProperty(required = true)
  private String password;
  @JsonProperty(required = true)
  private Date birthday;
  private String email;
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
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
  public Date getBirthday() {
    return birthday;
  }
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
}
