package com.ghbt.ghbt_starbucks.api.kakaopay.dto;

import com.ghbt.ghbt_starbucks.api.purchase.dto.ProductDetailResponse;
import com.ghbt.ghbt_starbucks.api.shipping_address.dto.ResponseShippingAddress;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseKakaoComplete {

    List<ProductDetailResponse> purchaseList;
    private String orderId;
    private String receiver;
    private String addressNickname;
    private String zipCode;
    private String detailAddress;
    private String baseAddress;
    private String phoneNumber1;
    private String notice;
    private Long shippingPrice;
    private String paymentType;
    private String cashReceipts;
    private Long totalPrice;

    public static ResponseKakaoComplete from(ResponseKakaoApprove responseKakaoApprove, List<OrderProductDto> products,
        ResponseShippingAddress shippingAddress, Long shippingPrice) {
        return ResponseKakaoComplete.builder()
            .orderId(responseKakaoApprove.getPartner_order_id())
            .receiver(shippingAddress.getReceiver())
            .addressNickname(shippingAddress.getAddressNickname())
            .zipCode(shippingAddress.getZipCode())
            .baseAddress(shippingAddress.getBaseAddress())
            .detailAddress(shippingAddress.getDetailAddress())
            .phoneNumber1(shippingAddress.getPhoneNumber1())
            .notice(shippingAddress.getNotice())
            .shippingPrice(shippingPrice)
            .totalPrice(Long.valueOf(responseKakaoApprove.getAmount().getTotal()))
            .purchaseList(products.stream().map(ProductDetailResponse::from).collect(Collectors.toList()))
            .paymentType("kakao-pay")
            .cashReceipts("none")
            .build();
    }
}
