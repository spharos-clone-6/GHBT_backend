package com.ghbt.ghbt_starbucks.api.auth.service;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user.repository.IUserRepository;
import com.ghbt.ghbt_starbucks.global.security.JwtTokenProvider;
import com.ghbt.ghbt_starbucks.global.security.dto.LoginDto;
import com.ghbt.ghbt_starbucks.global.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.global.security.dto.TokenDto;
import com.ghbt.ghbt_starbucks.global.security.redis.RedisService;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository iUserRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;
    private final String SERVER = "Server";

    //회원 가입
    @Transactional
    public Long signupUser(SignupDto signupDto) {
        User user = User.signinUser(signupDto);
        User savedUser = iUserRepository.save(user);
        return savedUser.getId();
    }

    //로그인
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return generateToken(SERVER, authentication.getName(), getAuthorities(authentication));
    }

    //로그아웃
    @Transactional
    public void logout(String requestAccessTokenInHeader) {
        String requestAccessToken = resolveAccessToken(requestAccessTokenInHeader);
        String principal = getPrincipal(requestAccessToken);

        String refreshTokenInRedis = redisService.getValues("RT(" + SERVER + "):" + principal);
        if (refreshTokenInRedis != null) {
            redisService.deleteValues("RT(" + SERVER + "):" + principal);
        }
        long expiration =
            jwtTokenProvider.getTokenExpirationTime(requestAccessToken) - new Date().getTime();
        redisService.setValuesWithTimeout(requestAccessToken,
            "logout",
            expiration);
    }

    // 토큰 발급
    @Transactional
    public TokenDto generateToken(String provider, String email, String authorities) {
        // RT가 이미 있을 경우
        if (redisService.getValues("RT(" + provider + "):" + email) != null) {
            redisService.deleteValues("RT(" + provider + "):" + email);
        }

        TokenDto tokenDto = jwtTokenProvider.createToken(email, authorities);
        saveRefreshToken(provider, email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    //토큰 재발급
    @Transactional
    public TokenDto reissue(String requestRefreshToken) {

        Authentication authentication = jwtTokenProvider.getAuthentication(requestRefreshToken);
        String principal = getPrincipal(requestRefreshToken);

        String refreshTokenInRedis = redisService.getValues("RT(" + SERVER + "):" + principal);
        if (refreshTokenInRedis == null) {
            return null;
        }

        if (!jwtTokenProvider.validateRefreshToken(requestRefreshToken) || !refreshTokenInRedis.equals(requestRefreshToken)) {
            redisService.deleteValues("RT(" + SERVER + "):" + principal);
            return null;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);

        redisService.deleteValues("RT(" + SERVER + "):" + principal);
        TokenDto tokenDto = jwtTokenProvider.createToken(principal, authorities);
        saveRefreshToken(SERVER, principal, tokenDto.getRefreshToken());
        return tokenDto;
    }

    //==편의 메서드 ==//
    public void saveRefreshToken(String provider, String principal, String refreshToken) {
        redisService.setValuesWithTimeout("RT(" + provider + "):" + principal, refreshToken,
            jwtTokenProvider.getTokenExpirationTime(refreshToken));
    }

    public String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    }

    public String getPrincipal(String requestAccessToken) {
        return jwtTokenProvider.getAuthentication(requestAccessToken).getName();
    }

    public String resolveAccessToken(String requestAccessTokenInHeader) {
        if (requestAccessTokenInHeader != null && requestAccessTokenInHeader.startsWith("Bearer ")) {
            return requestAccessTokenInHeader.substring(7);
        }
        return null;
    }
}
