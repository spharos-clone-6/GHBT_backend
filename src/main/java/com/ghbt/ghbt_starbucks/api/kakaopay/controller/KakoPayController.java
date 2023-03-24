package com.ghbt.ghbt_starbucks.api.kakaopay.controller;

import static com.ghbt.ghbt_starbucks.global.error.ErrorCode.*;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoApproveResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoReadyResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class KakoPayController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    public ResponseEntity<?> readyKakaoPay(@RequestBody KakaoPayOrderDto kakaoPayOrderDto) {
        KakaoReadyResponse kakaoReadyResponse = kakaoPayService.kakaoPayReady(kakaoPayOrderDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(kakaoReadyResponse);
    }

    @GetMapping("/success")
    public KakaoApproveResponse approveKakaoPay(@RequestParam("pg_token") String pgToken) {
        return kakaoPayService.approveResponse(pgToken);
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
