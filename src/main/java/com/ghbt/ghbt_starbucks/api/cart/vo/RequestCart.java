package com.ghbt.ghbt_starbucks.api.cart.vo;

import lombok.Data;

@Data
public class RequestCart {
    private Long productId;
    private Integer quantity;
}
