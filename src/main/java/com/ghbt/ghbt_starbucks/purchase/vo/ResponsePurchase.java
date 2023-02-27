package com.ghbt.ghbt_starbucks.purchase.vo;

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

}
