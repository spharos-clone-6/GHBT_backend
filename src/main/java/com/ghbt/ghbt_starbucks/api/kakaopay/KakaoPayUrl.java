package com.ghbt.ghbt_starbucks.api.kakaopay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KakaoPayUrl {
    READY_TO_POST("https://kapi.kakao.com/v1/payment/ready"),
    APPROVE_TO_POST("https://kapi.kakao.com/v1/payment/approve"),

    APPROVAL("http://localhost:8080/pay_success"),
    CANCEL("http://localhost:8080/payment/cancel"),
    FAIL("http://localhost:8080/payment/fail"),
    ;

    /**
     * backend.grapefruit-honey-black-tea.shop localhost:8080 localhost:3000
     */
    private String url;

}
