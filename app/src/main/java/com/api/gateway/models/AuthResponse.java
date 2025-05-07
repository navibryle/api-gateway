package com.api.gateway.models;

public class AuthResponse {
  public enum Status{
    OK,
    NOT_OK
  }

  private String bearerToken;

  private Status status;

  public AuthResponse(){
  }
  public AuthResponse(String bearerToken,Status status){
    this.bearerToken = bearerToken;
    this.status = status;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getBearerToken() {
    return bearerToken;
  }

  public void setBearerToken(String bearerToken) {
    this.bearerToken = bearerToken;
  }

}
