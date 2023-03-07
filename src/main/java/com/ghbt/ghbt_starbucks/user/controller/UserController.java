package com.ghbt.ghbt_starbucks.user.controller;

import com.ghbt.ghbt_starbucks.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.user.dto.ResponseUserDto;
import com.ghbt.ghbt_starbucks.user.dto.UpdateUserDto;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserServiceImpl userService;

  //회원 조회
  @GetMapping
  public ResponseEntity<ResponseUserDto> getUser(@LoginUser User loginUser) {
    User findUser = userService.getUser(loginUser.getId());
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseUserDto.from(findUser));
  }

  //회원 닉네임 수정
  @PatchMapping
  public ResponseEntity updateNickName(@LoginUser User loginUser, @RequestBody UpdateUserDto updateUserDto) {
    userService.updateUser(loginUser.getId(), updateUserDto);
    return ResponseEntity.status(HttpStatus.OK)
        .build();
  }
}
