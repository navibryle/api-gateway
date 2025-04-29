package com.api.gateway.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class SecurityUtil {
  private static SecretKey key = Jwts.SIG.HS256.key().build();
  public static String getJws(String userName){
    return Jwts.builder().subject(userName).signWith(key).compact();
 }

 public static boolean isJwsValid(String jws,String username){
  return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getSubject().equals(username);
 }
}
