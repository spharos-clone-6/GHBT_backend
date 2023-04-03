package com.ghbt.ghbt_starbucks.api.kakaopay.controller;

import static com.ghbt.ghbt_starbucks.global.error.ErrorCode.*;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoApproveResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoCompleteResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    @GetMapping("/kakaopay-approve")
    public ResponseEntity<KakaoCompleteResponse> approveKakaoPay(
        @RequestParam String pgToken,
        @LoginUser User loginUser) {
        KakaoCompleteResponse kakaoCompleteResponse = kakaoPayService.approveKakaopayment(pgToken, loginUser);
        return ResponseEntity.status(HttpStatus.OK)
            .body(kakaoCompleteResponse);
    }

    @GetMapping("/cancel")
    public void cancel() {
        throw new ServiceException(KAKAO_PAYMENT_CANCEL.getMessage(), KAKAO_PAYMENT_CANCEL.getHttpStatus());
    }

    @GetMapping("/fail")
    public void fail() {
        throw new ServiceException(KAKAO_PAYMENT_FAIL.getMessage(), KAKAO_PAYMENT_FAIL.getHttpStatus());
    }
}
