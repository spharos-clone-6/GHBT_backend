package com.ghbt.ghbt_starbucks.cart.service;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import com.ghbt.ghbt_starbucks.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.cart.vo.ResponseCart;


import java.util.List;

public interface ICartService {
    void addCart(RequestCart requestCart);
    ResponseCart getCart(Long cartId);
    List<ResponseCart> getAllCartByUserId(Long userId);

}
