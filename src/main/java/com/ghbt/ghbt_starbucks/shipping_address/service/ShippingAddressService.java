package com.ghbt.ghbt_starbucks.shipping_address.service;

import com.ghbt.ghbt_starbucks.shipping_address.dto.RequestShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.model.ShippingAddress;
import com.ghbt.ghbt_starbucks.shipping_address.repository.IShippingAddressRepository;
import com.ghbt.ghbt_starbucks.user.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShippingAddressService implements IShippingAddressService {

  private final IShippingAddressRepository iShippingAddressRepository;

  @Override
  @Transactional
  public Long saveShippingAddress(RequestShippingAddress requestShippingAddress, User user) {
    ShippingAddress newShippingAddress = requestShippingAddress.toEntity(requestShippingAddress,
        user);
    return iShippingAddressRepository.save(newShippingAddress).getId();
  }

  @Override
  @Transactional
  public Long updateShippingAddress(RequestShippingAddress requestUpdateShippingAddress,
      Long shippingAddressId) {
    ShippingAddress shippingAddress = iShippingAddressRepository.findById(shippingAddressId).get();

    shippingAddress.update(requestUpdateShippingAddress.getReceiver(),
        requestUpdateShippingAddress.getAddressNickname(),
        requestUpdateShippingAddress.getBaseAddress(),
        requestUpdateShippingAddress.getDetailAddress(),
        requestUpdateShippingAddress.getZipCode(),
        requestUpdateShippingAddress.getPhoneNumber1(),
        requestUpdateShippingAddress.getPhoneNumber2(),
        requestUpdateShippingAddress.getNotice(),
        requestUpdateShippingAddress.getIsDefault()
    );

    return shippingAddress.getId();
  }

  @Override
  public ResponseShippingAddress getDefaultShippingAddress(Long shippingAddressId) {
    ShippingAddress shippingAddress = iShippingAddressRepository.findById(shippingAddressId).get();
    return ResponseShippingAddress.from(shippingAddress);
  }

  @Override
  public List<ResponseShippingAddress> getAllShippingAddress() {

    List<ShippingAddress> shippingAddresses = iShippingAddressRepository.findAll();

    return shippingAddresses.stream()
        .map(ResponseShippingAddress::from)
        .collect(Collectors.toList());

  }

  @Override
  @Transactional
  public void deleteShippingAddress(Long shippingAddressId) {
    iShippingAddressRepository.deleteById(shippingAddressId);
  }


}
