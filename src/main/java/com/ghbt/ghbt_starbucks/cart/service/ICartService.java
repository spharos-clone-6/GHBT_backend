package com.ghbt.ghbt_starbucks.cart.service;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import com.ghbt.ghbt_starbucks.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.user.model.User;

import java.util.List;

public interface ICartService {
    void addCart(RequestCart requestCart);
    Cart getCart(Long cartId);
    List<Cart> getAllCartByUserId(Long userId);

}
