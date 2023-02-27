package com.ghbt.ghbt_starbucks.security.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupDto {

  private String email;
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
