package com.example.lifty.auth;

import com.example.lifty.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtProperties props;

  private SecretKey key() {
    return Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateAccessToken(String subjectEmail, Map<String, Object> extraClaims) {
    Instant now = Instant.now();
    Instant exp = now.plusSeconds(props.getAccessTokenMinutes() * 60);

    return Jwts.builder()
        .issuer(props.getIssuer())
        .subject(subjectEmail)
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .claims(extraClaims)
        .signWith(key())
        .compact();
  }

  public Claims parse(String token) {
    return Jwts.parser()
        .verifyWith(key())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public String getSubjectEmail(String token) {
    return parse(token).getSubject();
  }
}
