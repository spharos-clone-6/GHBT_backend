package com.ghbt.ghbt_starbucks.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestAuthCode {

    private String email;
    private String authCode;
}
