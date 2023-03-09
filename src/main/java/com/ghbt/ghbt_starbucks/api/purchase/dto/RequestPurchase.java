package com.ghbt.ghbt_starbucks.api.purchase.dto;

import com.ghbt.ghbt_starbucks.api.purchase.model.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPurchase {

    private Integer quantity;
    private String purchaseGroup;
    private ShippingStatus shippingStatus;
    private String shippingAddress;
    private String productId;
    private String productName;
    private Integer price;

}
