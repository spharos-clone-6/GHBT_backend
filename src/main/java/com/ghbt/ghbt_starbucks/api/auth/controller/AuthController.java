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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "상세 기능 : 회원가입폼에 입력된 정보로 회원가입을 진행합니다.")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDto signupDto) {
        String encodedPassword = encoder.encode(signupDto.getPassword());
        SignupDto signupDtoWithEncodedPassword = SignupDto.encodePassword(signupDto, encodedPassword);
        authService.signupUser(signupDtoWithEncodedPassword);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "상세 기능 : 로그인폼으로 회원을 인증합니다.")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {

        TokenDto tokenDto = authService.login(loginDto);

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
            .header(HttpHeaders.SET_COOKIE, "refresh-token=" + tokenDto.getRefreshToken()
                + "; domain= .grapefruit-honey-black-tea.shop; path=/; SameSite=None; Secure; httpOnly;")
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
}
