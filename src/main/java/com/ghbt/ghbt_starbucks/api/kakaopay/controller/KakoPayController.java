package com.ghbt.ghbt_starbucks.api.kakaopay.controller;

import static com.ghbt.ghbt_starbucks.global.error.ErrorCode.*;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoApproveResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoReadyResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class KakoPayController {

    private final KakaoPayService kakaoPayService;

    @GetMapping("/success")
    public void approveKakaoPay(@RequestParam("pg_token") String pgToken, HttpServletResponse response) throws IOException {
        log.info("[ 결제 승인 번호     ]: " + pgToken);
        response.sendRedirect("/api/payment/kakaopay-approve");
    }

    @GetMapping("/kakaopay-approve")
    public ResponseEntity<KakaoApproveResponse> approveKakaoPay(@RequestParam(value = "pg_token") String pgToken,
        @LoginUser User loginUser) {
        log.info(pgToken);
        KakaoApproveResponse kakaoApproveResponse = kakaoPayService.approveKakaopayment(pgToken, loginUser);
        return ResponseEntity.status(HttpStatus.OK)
            .body(kakaoApproveResponse);
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
