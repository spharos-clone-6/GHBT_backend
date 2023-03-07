package com.ghbt.ghbt_starbucks.shipping_address.controller;

import com.ghbt.ghbt_starbucks.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.shipping_address.dto.RequestShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.service.IShippingAddressService;
import com.ghbt.ghbt_starbucks.user.model.User;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipping-address")
public class ShippingAddressController {

  private final IShippingAddressService iShippingAddressService;

  //배송지 저장하기.
  @PostMapping
  public ResponseEntity saveShippingAddress(
      @RequestBody @Valid RequestShippingAddress requestShippingAddress,
      @LoginUser User loginUser) {

    iShippingAddressService.saveShippingAddress(loginUser, requestShippingAddress);
    return ResponseEntity.status(HttpStatus.CREATED)
        .build();
  }

  //배송지 불러오기.
  @GetMapping("/{shipping_address_id}")
  public ResponseEntity<ResponseShippingAddress> getShippingAddress(
      @PathVariable("shipping_address_id") Long shippingAddressId) {
    ResponseShippingAddress defaultShippingAddress = iShippingAddressService.getShippingAddress(
        shippingAddressId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(defaultShippingAddress);
  }

  //모든 배송지 불러오기.
  @GetMapping
  public ResponseEntity<Result> getAllShippingAddress(@LoginUser User loginUser) {
    List<ResponseShippingAddress> allUserShippingAddress = iShippingAddressService.getAllShippingAddress(
        loginUser);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Result<>(allUserShippingAddress));
  }

  //배송지 수정하기.
  @PutMapping("/{shipping_address_id}")
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
