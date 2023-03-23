package com.ghbt.ghbt_starbucks.api.auth.controller;

import com.ghbt.ghbt_starbucks.api.auth.service.AuthService;
import com.ghbt.ghbt_starbucks.global.security.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reissue")
@RequiredArgsConstructor
public class refreshTokenController {

    private final AuthService authService;

    /**
     * 토큰 재발급
     */
    @PostMapping
    @Operation(summary = "토큰 재발급 API", description = "상세 기능 : AccessToken 이 만료되었을 때 재발급해주는 API 입니다.")
    public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String refreshToken) {
        TokenDto reissuedTokenDto = authService.reissue(refreshToken);
        if (reissuedTokenDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, "refresh-token=" + reissuedTokenDto.getRefreshToken()
                    + "; domain= localhost; path=/; SameSite=None; Secure; httpOnly;")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
                .build();
        } else {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, "refresh-token=" + ""
                    + "; domain= localhost; Max-Age=0; path=/; SameSite=None; Secure; httpOnly;")
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
        }
    }
}
