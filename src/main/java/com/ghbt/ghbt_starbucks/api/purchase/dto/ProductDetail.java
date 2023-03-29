package com.ghbt.ghbt_starbucks.api.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {

    private Long productId;
    private String productName;
    private Integer productQuantity;
    private Long productPrice;

}
