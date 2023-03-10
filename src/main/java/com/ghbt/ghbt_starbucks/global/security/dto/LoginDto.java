package com.ghbt.ghbt_starbucks.global.security.dto;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @Builder
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
