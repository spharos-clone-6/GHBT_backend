package com.ghbt.ghbt_starbucks.security.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

  private String email;
  private String password;

  @Builder
  public LoginDto(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
