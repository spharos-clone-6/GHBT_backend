package com.ghbt.ghbt_starbucks.api.kakaopay.dto;

import com.ghbt.ghbt_starbucks.api.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {

    private Product product;
    private Long productQuantity;
}
