package com.ghbt.ghbt_starbucks.api.purchase.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPurchase {

    private List<ProductDetail> purchaseList;
    private String shippingAddress;
    private Long shippingPrice;

    private Long couponId;
    private Long couponPrice;

    private String paymentType;
    private String cashReceipts;
    private Long totalPrice;

}
