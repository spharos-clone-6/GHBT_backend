package com.ghbt.ghbt_starbucks.api.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestEmail {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
