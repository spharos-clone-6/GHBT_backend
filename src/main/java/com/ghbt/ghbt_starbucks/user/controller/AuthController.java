package com.ghbt.ghbt_starbucks.user.controller;

import com.ghbt.ghbt_starbucks.security.dto.LoginDto;
import com.ghbt.ghbt_starbucks.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.security.dto.TokenDto;
import com.ghbt.ghbt_starbucks.security.service.AuthService;
import com.ghbt.ghbt_starbucks.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final BCryptPasswordEncoder encoder;

  private final long COOKIE_EXPIRATION = 90 * 24 * 60 * 60;

  @PostMapping("/signup")
  public ResponseEntity<Void> signup(@RequestBody @Validated SignupDto signupDto) {
    String encodedPassword = encoder.encode(signupDto.getPassword());
    SignupDto signupDtoWithEncodedPassword = SignupDto.encodePassword(signupDto, encodedPassword);

    userService.signupUser(signupDtoWithEncodedPassword);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Validated LoginDto loginDto) {
    TokenDto tokenDto = authService.login(loginDto);

    HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
        .maxAge(COOKIE_EXPIRATION)
        .httpOnly(true)
        .secure(true)
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, httpCookie.toString())
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
        .build();
  }
}
