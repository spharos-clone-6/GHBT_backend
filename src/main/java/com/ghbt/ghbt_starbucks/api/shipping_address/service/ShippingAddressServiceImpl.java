package com.ghbt.ghbt_starbucks.api.shipping_address.service;

import static java.lang.Boolean.*;

import com.ghbt.ghbt_starbucks.api.shipping_address.repository.IShippingAddressRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.api.shipping_address.dto.RequestShippingAddress;
import com.ghbt.ghbt_starbucks.api.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.api.shipping_address.model.ShippingAddress;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements IShippingAddressService {

  private final IShippingAddressRepository iShippingAddressRepository;

  @Override
  @Transactional
  public Long saveShippingAddress(User loginUser, RequestShippingAddress requestShippingAddress) {

    List<ShippingAddress> allShippingAddress = iShippingAddressRepository.findAllByUserId(
        loginUser.getId());

    if (allShippingAddress.isEmpty()) {
      log.info("[배송지 저장] 최초의 배송지는 (기본)배송지로 저장됩니다.");
      requestShippingAddress.setIsDefault(true);

    } else if (requestShippingAddress.getIsDefault() == TRUE) {
      ShippingAddress defaultshippingAddress = allShippingAddress.stream()
          .filter(s -> s.getIsDefault() == TRUE)
          .findFirst()
          .get();
      defaultshippingAddress.changeIsDefault();
      log.info("[배송지 저장] 저장하려는 배송지가 (기본)배송지이면 기존의 (기본)배송지는 (일반)배송지로 전환됩니다.");
    }

    ShippingAddress newShippingAddress = requestShippingAddress.toEntity(requestShippingAddress,
        loginUser);
    log.info("[배송지 저장] 배송지가 성공적으로 저장되었습니다.");
    return iShippingAddressRepository.save(newShippingAddress).getId();
  }

  @Override
  @Transactional
  public Long updateShippingAddress(Long shippingAddressId,
      RequestShippingAddress requestUpdateShippingAddress) {
    List<ShippingAddress> allShippingAddress = iShippingAddressRepository.findAll();

    if (requestUpdateShippingAddress.getIsDefault() == TRUE) {
      ShippingAddress defaultShippingAddress = allShippingAddress.stream()
          .filter(s -> s.getIsDefault() == TRUE)
          .findFirst()
          .get();
      defaultShippingAddress.changeIsDefault();
      log.info("[배송지 업데이트] (기본)배송지가 있는데, (기본)배송지 설정을 하면 기존의 배송지는 (일반)배송지로 전환 됩니다.");
    }
    ShippingAddress findShippingAddress = allShippingAddress.stream()
        .filter(s -> s.getId() == shippingAddressId)
        .findFirst()
        .get();

    findShippingAddress.update(requestUpdateShippingAddress.getReceiver(),
        requestUpdateShippingAddress.getAddressNickname(),
        requestUpdateShippingAddress.getBaseAddress(),
        requestUpdateShippingAddress.getDetailAddress(),
        requestUpdateShippingAddress.getZipCode(),
        requestUpdateShippingAddress.getPhoneNumber1(),
        requestUpdateShippingAddress.getPhoneNumber2(),
        requestUpdateShippingAddress.getNotice(),
        requestUpdateShippingAddress.getIsDefault()
    );
    log.info("[배송지 업데이트] 배송지가 성공적으로 갱신되었습니다.");
    return findShippingAddress.getId();
  }

  @Override
  public ResponseShippingAddress getShippingAddress(Long shippingAddressId) {
    ShippingAddress shippingAddress = iShippingAddressRepository.findById(shippingAddressId)
        .orElseThrow(() -> new ServiceException("배송지가 존재하지 않습니다.", HttpStatus.NO_CONTENT));
    log.info("[배송지 조회] 배송지 1건이 성공적으로 조회되었습니다.");
    return ResponseShippingAddress.from(shippingAddress);
  }

  @Override
  public List<ResponseShippingAddress> getAllShippingAddress(User loginUser) {
    List<ShippingAddress> shippingAddresses = iShippingAddressRepository.findAllByUserId(
        loginUser.getId());
    if (shippingAddresses.isEmpty()) {
      throw new ServiceException("배송지가 존재하지 않습니다.", HttpStatus.NO_CONTENT);
    }
    log.info("[배송지 조회] 배송지 전체가 성공적으로 조회되었습니다.");
    return shippingAddresses.stream()
        .map(ResponseShippingAddress::from)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void deleteShippingAddress(Long shippingAddressId) {
    log.info("[배송지 삭제] 배송지가 성공적으로 삭제되었습니다.");
    iShippingAddressRepository.deleteById(shippingAddressId);
  }
}