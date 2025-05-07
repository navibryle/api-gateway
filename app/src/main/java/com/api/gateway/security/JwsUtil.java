package com.api.gateway.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwsUtil {
  private SecretKey key = Jwts.SIG.HS256.key().build();

  public String getJws(String userName){
    return Jwts.builder().subject(userName).signWith(key).compact();
 }

 public boolean isJwsValid(String jws){
  try {
    Jwts.parser().verifyWith(key).build().parseSignedClaims(jws);
    return true;
  } catch (JwtException e) {
    return false;
  }
 }

}
