package com.ghbt.ghbt_starbucks.cart.vo;

import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCart {
    private Integer quantity;
    private User user;
    private Product product;

}
