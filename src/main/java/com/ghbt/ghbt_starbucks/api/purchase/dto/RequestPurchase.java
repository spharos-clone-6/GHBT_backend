package com.ghbt.ghbt_starbucks.api.purchase.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPurchase {

    private String shippingAddress;
    private String paymentType;
    private Long productId;
    private String productName;
    private Integer productQuantity;
    private Long productPrice;
    private Long shippingPrice;
    private Integer couponPrice;
    private Long couponId;
    private String cashReceipts;
    private Long totalPrice;

}
