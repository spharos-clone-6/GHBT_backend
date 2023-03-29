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
public class RequestPurchases {

    //장바구니 다건구매시 적용할 RequestDTO
    private List<RequestCarts> purchaseList;
    private Long shippingPrice;
    private String shippingAddress;
    private String paymentType;
    private Integer couponPrice;
    private Long couponId;
    private String cashReceipts;
    private Long totalPrice;

}
