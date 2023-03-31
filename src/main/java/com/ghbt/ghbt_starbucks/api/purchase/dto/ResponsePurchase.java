package com.ghbt.ghbt_starbucks.api.purchase.dto;

import com.ghbt.ghbt_starbucks.api.purchase.model.Purchase;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponsePurchase {

    private String shippingAddress;

    private Integer quantity;

    private Long productId;

    private String productName;

    private String uuid;

    private Long price;

    public static ResponsePurchase from(Purchase purchase) {
        return ResponsePurchase.builder()
            .quantity(purchase.getQuantity())
            .shippingAddress(purchase.getShippingAddress())
            .productId(purchase.getProductId())
            .productName(purchase.getProductName())
            .uuid(purchase.getUuid())
            .price(purchase.getPrice())
            .build();
    }
}
