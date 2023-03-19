package com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StarbucksCardPaymentDto {

    private Long remainPaymentPrice;
    private Long remainStarbucksCardPrice;
}
