package com.ghbt.ghbt_starbucks.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseEmailAuthCode {

    private String authCode;
}
