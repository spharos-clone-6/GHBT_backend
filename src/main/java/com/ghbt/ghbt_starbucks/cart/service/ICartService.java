package com.ghbt.ghbt_starbucks.cart.service;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.user.model.User;

public interface ICartService {
    void addCart(Product product, User user);
    Cart getCart(Long cartId);
    Cart getAllCartByUserId(Long userId);

}
