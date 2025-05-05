package com.api.gateway.models;

public class ApiModel {
  private String srcPath;

  private String target;

  private boolean isProtected;

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public boolean isProtected() {
    return isProtected;
  }

  public void setProtected(boolean isProtected) {
    this.isProtected = isProtected;
  }

  public String getSrcPath() {
    return srcPath;
  }

  public void setSrcPath(String srcPath) {
    this.srcPath = srcPath;
  }
  public ApiModel(String srcPath,String target,boolean isProtected){
    this.srcPath = srcPath;
    this.target = target;
    this.isProtected = isProtected;
  }
}
