package com.ghbt.ghbt_starbucks.api.kakaopay.dto;

import lombok.Getter;

@Getter
public class KakaoReadyResponse {

    private String tid;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String created_at;

}
