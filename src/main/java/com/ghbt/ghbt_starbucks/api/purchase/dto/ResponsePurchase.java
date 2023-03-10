package com.ghbt.ghbt_starbucks.api.purchase.dto;

import com.ghbt.ghbt_starbucks.api.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.api.purchase.model.ShippingStatus;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponsePurchase {

    private Long id;

    private User user;

    private Integer quantity;

    private String purchaseGroup;

    private ShippingStatus shippingStatus;

    private String shippingAddress;

    private String productId;

    private String productName;

    private String uuid;

    private Integer price;

    public static ResponsePurchase from(Purchase purchase) {
        return ResponsePurchase.builder()
            .id(purchase.getId())
            .user(purchase.getUser())
            .quantity(purchase.getQuantity())
            .purchaseGroup(purchase.getPurchaseGroup())
            .shippingStatus(purchase.getShippingStatus())
            .shippingAddress(purchase.getShippingAddress())
            .productId(purchase.getProductId())
            .productName(purchase.getProductName())
            .uuid(purchase.getUuid())
            .price(purchase.getPrice())
            .build();
    }
}
