package com.ghbt.ghbt_starbucks.api.auth.controller;

import com.ghbt.ghbt_starbucks.api.auth.service.AuthService;
import com.ghbt.ghbt_starbucks.global.security.dto.LoginDto;
import com.ghbt.ghbt_starbucks.global.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.global.security.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입/로그인/로그아웃/jwt 재발급")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;

    private final long COOKIE_EXPIRATION = 90 * 24 * 60 * 60L;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "상세 기능 : 회원가입폼에 입력된 정보로 회원가입을 진행합니다.")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupDto signupDto) {
        String encodedPassword = encoder.encode(signupDto.getPassword());
        SignupDto signupDtoWithEncodedPassword = SignupDto.encodePassword(signupDto, encodedPassword);
        authService.signupUser(signupDtoWithEncodedPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "상세 기능 : 로그인폼으로 회원을 인증합니다.")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
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

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "상세 기능 : 로그인한 유저를 로그아웃을합니다.")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
            .maxAge(0)
            .path("/")
            .build();
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
            .build();
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급 API", description = "상세 기능 : AccessToken 이 만료되었을 때 재발급해주는 API 입니다.")
    public ResponseEntity<?> reissue(
        @CookieValue(name = "refresh-token") String refreshToken,
        @RequestHeader(name = "Authorization") String accessToken) {

        TokenDto reissuedTokenDto = authService.reissue(accessToken, refreshToken);
        if (reissuedTokenDto != null) {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
                .maxAge(COOKIE_EXPIRATION)
                .httpOnly(true)
                .secure(true)
                .build();
            return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
                .build();
        } else {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
        }
    }
}
