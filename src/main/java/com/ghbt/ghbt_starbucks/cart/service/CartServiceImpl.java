package com.ghbt.ghbt_starbucks.cart.service;

import com.ghbt.ghbt_starbucks.cart.model.Cart;

import com.ghbt.ghbt_starbucks.cart.repository.ICartRepository;
import com.ghbt.ghbt_starbucks.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements ICartService{


    private final ICartRepository iCartRepository;
    private final IUserRepository iUserRepository;
    private final IProductRepository iProductRepository;

    @Override
    public void addCart(RequestCart requestCart) {
        Product product = iProductRepository.findById(requestCart.getProductId()).get();

        User user = iUserRepository.findById(requestCart.getUserId()).get();

        Cart cart = Cart.builder()
                .product(product)
                .user(user)
                .quantity(requestCart.getQuantity())
                .build();
    }

    @Override
    public Cart getCart(Long cartId) {
        return iCartRepository.findById(cartId).get();
    }

    @Override
    public List<Cart> getAllCartByUserId(Long userId) {
         return iCartRepository.findAllByUser_Id(userId);
    }
}
