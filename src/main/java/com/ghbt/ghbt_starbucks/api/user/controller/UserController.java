package com.ghbt.ghbt_starbucks.api.user.controller;

import com.ghbt.ghbt_starbucks.api.user.dto.ResponseUserDto;
import com.ghbt.ghbt_starbucks.api.user.dto.UpdateUserDto;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user.service.UserServiceImpl;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "basicAuth")
public class UserController {

  private final UserServiceImpl userService;

  //회원 조회
  @GetMapping
  @Operation(summary = "유저 조회", description = "상세 기능 : 로그인한 유저의 상세 정보를 출력합니다.")
  public ResponseEntity<ResponseUserDto> getUser(@LoginUser User loginUser) {
    User findUser = userService.getUser(loginUser.getId());
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseUserDto.from(findUser));
  }

  //회원 닉네임 수정
  @PatchMapping
  @Operation(summary = "닉네임 수정", description = "상세 기능 : 닉네임 수정 폼의 정보로 로그인한 유저의 닉네임을 변경합니다.")
  public ResponseEntity updateNickName(@LoginUser User loginUser, @RequestBody UpdateUserDto updateUserDto) {
    userService.updateUser(loginUser.getId(), updateUserDto);
    return ResponseEntity.status(HttpStatus.OK)
        .build();
  }
}
