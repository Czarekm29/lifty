package com.example.lifty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
  private String secret;
  private String issuer;
  private long accessTokenMinutes = 60;
}
