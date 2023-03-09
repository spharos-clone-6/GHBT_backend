package com.ghbt.ghbt_starbucks.api.shipping_address.dto;

import com.ghbt.ghbt_starbucks.api.shipping_address.model.ShippingAddress;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestShippingAddress {

  @NotEmpty
  private String receiver;

  @NotEmpty
  private String zipCode;

  @NotEmpty
  private String addressNickname;

  @NotEmpty
  private String detailAddress;

  @NotEmpty
  private String baseAddress;

  @NotEmpty
  private String phoneNumber1;

  private String phoneNumber2;

  private String notice;

  private Boolean isDefault;

  public ShippingAddress toEntity(RequestShippingAddress requestShippingAddress, User user) {
    return ShippingAddress.builder()
        .user(user)
        .receiver(requestShippingAddress.getReceiver())
        .zipCode(requestShippingAddress.getZipCode())
        .addressNickname(requestShippingAddress.getAddressNickname())
        .detailAddress(requestShippingAddress.getDetailAddress())
        .baseAddress(requestShippingAddress.getBaseAddress())
        .phoneNumber1(requestShippingAddress.getPhoneNumber1())
        .phoneNumber2(requestShippingAddress.getPhoneNumber2())
        .notice(requestShippingAddress.getNotice())
        .isDefault(requestShippingAddress.getIsDefault())
        .build();
  }
}
