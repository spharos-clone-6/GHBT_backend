package com.ghbt.ghbt_starbucks.cart.service;

import com.ghbt.ghbt_starbucks.cart.model.Cart;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImp implements ICartService{

    @Override
    public void addCart(Product product, User user) {

    }

    @Override
    public Cart getCart(Long cartId) {
        return null;
    }

    @Override
    public Cart getAllCartByUserId(Long userId) {
        return null;
    }
}
