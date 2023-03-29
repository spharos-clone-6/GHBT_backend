package com.ghbt.ghbt_starbucks.api.purchase.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProcessStatus {
    PAYMENT_INCOMPLETE("결제 준비중"), PAYMENT_COMPLETE("결제 완료"), PREPARING_PRODUCT("상품 준비중"),
    ;
    private String message;
}
