package com.ghbt.ghbt_starbucks.api.mobile_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEnrollMobileCard {

    private String cardName;
    private String thumbnailUrl;

    private String cardNumber; //16자리 필수
    private String pinNumber;  //8자리
}
