package com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestStarbucksCard {

    private String cardNickName;
    private String cardNumber;
    private String pinNumber;
}
