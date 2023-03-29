package com.ghbt.ghbt_starbucks.api.kakaopay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KakoPayUrl {
    READY_TO_POST("https://kapi.kakao.com/v1/payment/ready"),
    APPROVE_TO_POST("https://kapi.kakao.com/v1/payment/approve"),
    APPROVAL("http://backend.grapefruit-honey-black-tea.shop/api/payment/success"),
    CANCEL("http://localhost:8080/api/payment/cancel"),
    FAIL("http://localhost:8080/api/payment/fail"),
    ;

    //backend.grapefruit-honey-black-tea.shop
    private String url;

}
