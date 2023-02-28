package com.ghbt.ghbt_starbucks.shipping_address.controller;

import com.ghbt.ghbt_starbucks.shipping_address.dto.RequestShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.service.IShippingAddressService;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.IUserRepository;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/shipping-address")
public class ShippingAddressController {

  private final IShippingAddressService iShippingAddressService;
  private final IUserRepository iUserRepository;

  @PostMapping("")
  public ResponseEntity saveShippingAddress(
      @RequestBody @Valid RequestShippingAddress requestShippingAddress,
      Authentication authentication) {

    User loginUser = iUserRepository.findByEmail(authentication.getName()).get();
    iShippingAddressService.saveShippingAddress(requestShippingAddress, loginUser);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("{shipping_address_id}")
  public ResponseShippingAddress getDefaultShippingAddress(
      @PathVariable("shipping_address_id") Long shippingAddressId) {
    ResponseShippingAddress defaultShippingAddress = iShippingAddressService.getDefaultShippingAddress(
        shippingAddressId);
    return defaultShippingAddress;
  }

  @GetMapping("")
  public Result getAllShippingAddress(Authentication authentication) {
    List<ResponseShippingAddress> allUserShippingAddress = iShippingAddressService.getAllShippingAddress();
    return new Result(allUserShippingAddress);
  }

  @PutMapping("/{shipping_address_id}")
  public ResponseEntity updateShippingAddress(
      @PathVariable("shipping_address_id") Long shippingAddressId,
      @RequestBody @Valid
      RequestShippingAddress requestShippingAddress) {
    iShippingAddressService.updateShippingAddress(requestShippingAddress, shippingAddressId);

    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping("/{shipping_address_id}")
  public ResponseEntity deleteShippingAddress(
      @PathVariable("shipping_address_id") Long shippingAddressId) {
    iShippingAddressService.deleteShippingAddress(shippingAddressId);

    return new ResponseEntity(HttpStatus.OK);
  }


  @Data
  @AllArgsConstructor
  static class Result<T> {

    private T shippingAddress;
  }
}
