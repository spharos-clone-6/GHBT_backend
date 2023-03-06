package com.ghbt.ghbt_starbucks.purchase.dto;

import com.ghbt.ghbt_starbucks.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.purchase.model.ShippingStatus;
import com.ghbt.ghbt_starbucks.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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