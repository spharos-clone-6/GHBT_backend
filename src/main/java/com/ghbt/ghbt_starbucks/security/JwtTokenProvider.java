package com.ghbt.ghbt_starbucks.security;

import com.ghbt.ghbt_starbucks.security.dto.TokenDto;
import com.ghbt.ghbt_starbucks.security.redis.RedisService;
import com.ghbt.ghbt_starbucks.security.userdetail.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional(readOnly = true)
public class JwtTokenProvider implements InitializingBean {

  private final UserDetailsServiceImpl userDetailsService;
  private final RedisService redisService;

  private static final String AUTHORITIES_KEY = "role";
  private static final String EMAIL_KEY = "email";
  private static final String URL = "http://localhost:8080";

  private final String secretKey;
  private static Key signingKey;

  private final Long ACCESS_TOKEN_VALIDATE_MILLISECONDS = 60 * 60 * 12L;
  private final Long REFRESH_TOKEN_VALIDATE_MILLISECONDS = 60 * 60 * 12 * 14L;

  public JwtTokenProvider(UserDetailsServiceImpl userDetailsService,
      RedisService redisService,
      @Value("${jwt.secret}") String secretKey) {
    this.userDetailsService = userDetailsService;
    this.redisService = redisService;
    this.secretKey = secretKey;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public TokenDto createToken(String email, String authorities) {
    Long now = System.currentTimeMillis();

    String accessToken = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setHeaderParam("alg", "HS512")
        .setExpiration(new Date(now + ACCESS_TOKEN_VALIDATE_MILLISECONDS))
        .setSubject("access-token")
        .claim(URL, true)
        .claim(EMAIL_KEY, email)
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(signingKey, SignatureAlgorithm.HS512)
        .compact();

    String refreshToken = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setHeaderParam("alg", "HS512")
        .setExpiration(new Date(now + REFRESH_TOKEN_VALIDATE_MILLISECONDS))
        .setSubject("refresh-token")
        .signWith(signingKey, SignatureAlgorithm.HS512)
        .compact();

    return new TokenDto(accessToken, refreshToken);
  }

  //==토큰으로부터 정보 추출==//
  public Claims getClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(signingKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  public Authentication getAuthentication(String token) {
    String email = getClaims(token).get(EMAIL_KEY).toString();
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    return new UsernamePasswordAuthenticationToken(userDetails, "",
        userDetails.getAuthorities());
  }

  public long getTokenExpirationTime(String token) {
    return getClaims(token)
        .getExpiration()
        .getTime();
  }

  //== 토큰 검증 ==//
  public boolean validateRefreshToken(String refreshToken) {
    try {
      if (redisService.getValues(refreshToken).equals("delete")) {
        return false;
      }
      Jwts.parserBuilder()
          .setSigningKey(signingKey)
          .build()
          .parseClaimsJws(refreshToken);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature.");
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token.");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token.");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token.");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty");
    } catch (NullPointerException e) {
      log.error("JWT Token is empty");
    }
    return false;
  }

  //Filter 에서 사용
  public boolean validateAccessToken(String accessToken) {
    try {
      if (redisService.getValues(accessToken) != null && redisService.getValues(accessToken)
          .equals("logout")) {
        return false;
      }
      Jwts.parserBuilder()
          .setSigningKey(signingKey)
          .build()
          .parseClaimsJws(accessToken);
      return true;
    } catch (ExpiredJwtException e) {
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  //재발급 검증 API에서 사용
  public boolean validateAccessTokenOnlyExpired(String accessToken) {
    try {
      return getClaims(accessToken)
          .getExpiration()
          .before(new Date());
    } catch (ExpiredJwtException e) {
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
