package com.ghbt.ghbt_starbucks.api.kakaopay.dto;

import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePayment;
import lombok.Getter;

@Getter
public class ResponseKakaoReady extends ResponsePayment {

    private String tid;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String created_at;

}
