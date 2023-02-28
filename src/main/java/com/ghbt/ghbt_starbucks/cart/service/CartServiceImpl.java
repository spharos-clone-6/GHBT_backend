package com.ghbt.ghbt_starbucks.cart.service;

import com.ghbt.ghbt_starbucks.cart.model.Cart;

import com.ghbt.ghbt_starbucks.cart.repository.ICartRepository;
import com.ghbt.ghbt_starbucks.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.cart.vo.ResponseCart;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        iCartRepository.save(cart);
    }

    @Override
    public ResponseCart getCart(Long cartId) {
        Cart cart = iCartRepository.findById(cartId).get();

        return ResponseCart.builder()
                .product(cart.getProduct())
                .quantity(cart.getQuantity())
                .user(cart.getUser())
                .build();

    }

    @Override
    public List<ResponseCart> getAllCartByUserId(Long userId) {
        ModelMapper modelMapper = new ModelMapper();
        List<Cart> carts= iCartRepository.findAllByUser_Id(userId);

        List<ResponseCart> responseCartList = new ArrayList<>();
        carts.forEach(cart -> {
            ResponseCart responseCart = modelMapper.map(cart,ResponseCart.class);
            responseCartList.add(responseCart);
        });

        return responseCartList;

    }
}
