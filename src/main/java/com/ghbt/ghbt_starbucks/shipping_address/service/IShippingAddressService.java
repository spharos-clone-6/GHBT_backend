package com.ghbt.ghbt_starbucks.shipping_address.service;

import com.ghbt.ghbt_starbucks.shipping_address.dto.RequestShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.user.model.User;
import java.util.List;

public interface IShippingAddressService {

  //배송지 1건 저장.
  Long saveShippingAddress(RequestShippingAddress requestShippingAddress, User user);

  //기본 배송지 조회.
  ResponseShippingAddress getShippingAddress(Long shippingAddressId);

  //모든 배송지 조회.
  List<ResponseShippingAddress> getAllShippingAddress();

  //배송지 삭제
  void deleteShippingAddress(Long ShippingAddressId);

  //배송지 수정
  Long updateShippingAddress(RequestShippingAddress requestUpdateShippingAddress,
      Long ShippingAddressId);
}
