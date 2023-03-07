package com.ghbt.ghbt_starbucks.user.service;

import com.ghbt.ghbt_starbucks.security.dto.SignupDto;
import com.ghbt.ghbt_starbucks.user.dto.UpdateUserDto;
import com.ghbt.ghbt_starbucks.user.model.User;

public interface IUserService {

  //유저 등록
  Long signupUser(SignupDto signupDto);

  //유저 조회
  User getUser(Long userId);

  //유저 수정
  Long updateUser(Long userId, UpdateUserDto updateUserDto);

  //유저 삭제
  void deleteUser(Long userId);
}
