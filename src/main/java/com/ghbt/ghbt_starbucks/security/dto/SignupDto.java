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
    SignupDto signDtoWithEncodedPassword = new SignupDto();
    signDtoWithEncodedPassword.email = signupDto.getEmail();
    signDtoWithEncodedPassword.password = encodedPassword;
    return signDtoWithEncodedPassword;
  }
}
