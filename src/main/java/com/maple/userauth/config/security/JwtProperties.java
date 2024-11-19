package com.maple.userauth.config.security;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties("spring.security.jwt")
public class JwtProperties {

  private final long expireTimeInMillis = 86400000;
  private final String secretKey = "somesecretkeyformapleprojectsomesecretkeyformapleproject";
}
