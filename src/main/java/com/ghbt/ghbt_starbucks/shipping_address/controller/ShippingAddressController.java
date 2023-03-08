package com.ghbt.ghbt_starbucks.shipping_address.controller;

import com.ghbt.ghbt_starbucks.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.shipping_address.dto.RequestShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.service.IShippingAddressService;
import com.ghbt.ghbt_starbucks.user.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배송지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipping-address")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "basicAuth")
public class ShippingAddressController {

  private final IShippingAddressService iShippingAddressService;

  //배송지 저장하기.
  @PostMapping
  @Operation(summary = "배송지 저장", description = "상세 기능 : 배송지폼에 입력된 정보를 저장합니다.")
  public ResponseEntity saveShippingAddress(
      @RequestBody @Valid RequestShippingAddress requestShippingAddress,
      @LoginUser User loginUser) {

    iShippingAddressService.saveShippingAddress(loginUser, requestShippingAddress);
    return ResponseEntity.status(HttpStatus.CREATED)
        .build();
  }

  //배송지 불러오기.
  @GetMapping("/{shipping_address_id}")
  @Operation(summary = "유저 배송지 1건 조회", description = "상세 기능 : 배송지 아이디를 이용하여, 배송지 1건을 조회합니다.")
  public ResponseEntity<ResponseShippingAddress> getShippingAddress(
      @PathVariable("shipping_address_id") Long shippingAddressId) {
    ResponseShippingAddress defaultShippingAddress = iShippingAddressService.getShippingAddress(
        shippingAddressId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(defaultShippingAddress);
  }

  //모든 배송지 불러오기.
  @GetMapping
  @Operation(summary = "유저 배송지 전체 조회", description = "상세 기능 : 유저 아이디를 이용하여, 배송지 전체를 조회합니다.")
  public ResponseEntity<Result> getAllShippingAddress(@LoginUser User loginUser) {
    List<ResponseShippingAddress> allUserShippingAddress = iShippingAddressService.getAllShippingAddress(
        loginUser);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Result<>(allUserShippingAddress));
  }

  //배송지 수정하기.
  @PatchMapping("/{shipping_address_id}")
  @Operation(summary = "배송지 수정", description = "상세 기능 : 수정 배송지 폼을 이용하여 기존의 배송지를 업데이트합니다.")
  public ResponseEntity updateShippingAddress(
      @PathVariable("shipping_address_id") Long shippingAddressId,
      @RequestBody @Valid
      RequestShippingAddress requestShippingAddress) {
    iShippingAddressService.updateShippingAddress(shippingAddressId, requestShippingAddress);

    return ResponseEntity.status(HttpStatus.OK)
        .build();
  }

  //배송지 삭제.
  @DeleteMapping("/{shipping_address_id}")
  @Operation(summary = "배송지 삭제", description = "상세 기능 : 배송지 아이디를 이용하여 배송지를 삭제합니다.")
  public ResponseEntity deleteShippingAddress(
      @PathVariable("shipping_address_id") Long shippingAddressId) {
    iShippingAddressService.deleteShippingAddress(shippingAddressId);

    return ResponseEntity.status(HttpStatus.OK)
        .build();
  }

  @Data
  @AllArgsConstructor
  static class Result<T> {

    private T shippingAddress;
  }
}
