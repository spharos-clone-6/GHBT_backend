package com.ghbt.ghbt_starbucks.global.security.dto;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupDto {
  @NotBlank
  private String email;
  @NotBlank
  private String password;

  @Builder
  public SignupDto(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public static SignupDto encodePassword(SignupDto signupDto, String encodedPassword) {
    SignupDto signupDtoWithEncodedPassword = new SignupDto();
    signupDtoWithEncodedPassword.email = signupDto.getEmail();
    signupDtoWithEncodedPassword.password = encodedPassword;
    return signupDtoWithEncodedPassword;
  }
}
