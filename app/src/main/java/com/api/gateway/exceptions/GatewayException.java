package com.api.gateway.exceptions;

public class GatewayException extends Exception{

  public enum MSG{
    REQ_FAILED,
    MULTIPLE_REGISTER,
  }


  private static String getMsg(MSG msg){
    switch (msg) {
      case REQ_FAILED:
        return "The request to the target server has failed with status:";
      case MULTIPLE_REGISTER:
        return "Api gateway has more than one registration endpoint";
      default:
          return "Error";
    } 
  }

  public GatewayException(MSG msg){
    super(getMsg(msg));
  }
  public GatewayException(MSG msg,String errorMsg){
    super(getMsg(msg)+" "+errorMsg);
  }
}
