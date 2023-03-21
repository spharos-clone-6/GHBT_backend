package com.ghbt.ghbt_starbucks.api.cart.vo;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.user.model.User;
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
    private Boolean deleted;

    private Boolean checked;

    public static ResponseCart from(Cart cart) {
        return ResponseCart.builder()
            .deleted(cart.getDeleted())
            .user(cart.getUser())
            .product(cart.getProduct())
            .quantity(cart.getQuantity())
            .checked(cart.getChecked())
            .build();
    }
}
