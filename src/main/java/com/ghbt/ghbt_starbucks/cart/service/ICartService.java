package com.ghbt.ghbt_starbucks.cart.service;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import com.ghbt.ghbt_starbucks.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.cart.vo.ResponseCart;
import com.ghbt.ghbt_starbucks.user.model.User;


import java.util.List;

public interface ICartService {
    void addCart(RequestCart requestCart, User loginUser);
    ResponseCart getCart(Long cartId);
    List<ResponseCart> getAllCartByUserId(Long userId);

    void deleteCart(Long Id);

    ResponseCart updateCart(Long cartId, Integer quantity);
}
