package com.ghbt.ghbt_starbucks.global.security.dto;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String adAgreement;

    public static SignupDto encodePassword(SignupDto signupDto, String encodedPassword) {
        SignupDto signupDtoWithEncodedPassword = new SignupDto();
        signupDtoWithEncodedPassword.email = signupDto.getEmail();
        signupDtoWithEncodedPassword.adAgreement = signupDto.getAdAgreement();
        signupDtoWithEncodedPassword.password = encodedPassword;
        return signupDtoWithEncodedPassword;
    }
}
