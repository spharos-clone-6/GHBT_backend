package com.ghbt.ghbt_starbucks.api.cart.service;

import com.ghbt.ghbt_starbucks.api.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.api.cart.vo.ResponseCart;
import com.ghbt.ghbt_starbucks.api.user.model.User;


import java.util.List;

public interface ICartService {

    void addCart(RequestCart requestCart, User loginUser);

    ResponseCart getCart(Long cartId);

    List<ResponseCart> getAllCartByUserId(Long userId);

    List<ResponseCart> getAllCartByUserIdAndIce(Long userId);

    void deleteCart(Long Id);

    ResponseCart updateCart(Long cartId, Integer quantity);
}
