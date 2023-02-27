package com.ghbt.ghbt_starbucks.cart.vo;

import lombok.Data;

@Data
public class RequestCart {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
