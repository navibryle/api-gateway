package com.api.gateway.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class SecurityUtil {
  private static SecretKey key = Jwts.SIG.HS256.key().build();
  public static String getJws(String userName){
    return Jwts.builder().subject(userName).signWith(key).compact();
 }

 public static boolean isJwsValid(String jws,String username){
  try {
    Jwts.parser().verifyWith(key).build().parseSignedClaims(jws);
    return true;
  } catch (JwtException e) {
    return false;
  }
 }

}
