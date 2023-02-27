package com.ghbt.ghbt_starbucks.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  ADMIN("ROLE_ADMIN", "관리자"),
  USER("ROLE_USER", "사용자"),
  ;
  private final String roleCode;
  private final String roleName;

}
