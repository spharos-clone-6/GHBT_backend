package com.ghbt.ghbt_starbucks.global.swagger.util;

import com.ghbt.ghbt_starbucks.global.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.api.user.service.UserServiceImpl;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SwaggerTestUser {

  private final FastSwaggerStarter fastSwaggerStarter;

  @PostConstruct
  public void init() {
    fastSwaggerStarter.generateUserAndSignup();
  }

  @Component
  @RequiredArgsConstructor
  @Transactional
  private static class FastSwaggerStarter {

    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder encoder;

    public void generateUserAndSignup() {

      userService.signupUser(SignupDto
          .builder()
          .email("aaa@spharos.co.kr")
          .password(encoder.encode("aaa"))
          .build());

      log.info("[Swagger 로그인 유저 Bearer 입니다. 복사해서 사용해주세요.] " +
          "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NzgyNDU4MjQsInN1YiI6ImFjY2Vzcy10b"
          + "2tlbiIsImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCI6dHJ1ZSwiZW1haWwiOiJhYWFAc3BoYXJvcy5jby5rciIs"
          + "InJvbGUiOiJST0xFX1VTRVIifQ.uX0HWqSgxP7nI0gXcaDg1yaMJHkUtd-NS-zK8eDrBb94c__0j4ttHCqoD"
          + "x7x511h-2lAxxu87EfceMfLEEhjXQ");

    }
  }
}