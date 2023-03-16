package com.ghbt.ghbt_starbucks.api.mobile_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMobileCardToEnroll {

    private String name;
    private String type;
    private String cardNumber;
    private String pinNumber;
    private String thumbnailUrl;
    private Long price;

}
