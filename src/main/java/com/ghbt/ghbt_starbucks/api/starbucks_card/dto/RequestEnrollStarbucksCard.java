package com.ghbt.ghbt_starbucks.api.starbucks_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEnrollStarbucksCard {

    private String cardName;
    private String thumbnailUrl;

    private String cardNumber; //16자리 필수
    private String pinNumber;  //8자리
}
