package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMobileCard {

    private String cardNickName;
    private String cardNumber;
    private String pinNumber;
}
