package com.maple.userauth.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class JwtHelper {

  private final JwtProperties jwtProperties;

  public String generateJwtToken(final Authentication authentication) {
    val userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder()
        .subject((userPrincipal.getUsername()))
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + jwtProperties.getExpireTimeInMillis()))
        .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
        .compact();
  }

  public String getUserNameFromJwtToken(String authToken) {

    return Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
        .build()
        .parseSignedClaims(authToken)
        .getPayload()
        .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser()
          .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
          .build()
          .parseSignedClaims(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
