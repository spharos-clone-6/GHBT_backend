package com.ghbt.ghbt_starbucks.api.cart.service;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import com.ghbt.ghbt_starbucks.api.cart.repository.ICartRepository;
import com.ghbt.ghbt_starbucks.api.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.api.cart.vo.ResponseCart;

import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user.repository.IUserRepository;
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
    public void addCart(RequestCart requestCart,User loginUser) {

        //삭제된 cart_list 인지 확인

        ICartRepository.FindOneCartId preCheckCartId = iCartRepository.findAllByDeletedId(loginUser.getId(),requestCart.getProductId());

        //삭제된 cart_list 라면 복구
        if(preCheckCartId != null) {
            Cart preCart = iCartRepository.findById(preCheckCartId.getId()).get();
            preCart.setDeleted(false);
            preCart.setQuantity(requestCart.getQuantity());
            iCartRepository.save(preCart);

            return ;
        }

        Product product = iProductRepository.findById(requestCart.getProductId()).get();

        User user = iUserRepository.findById(loginUser.getId()).get();

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

    @Override
    public void deleteCart(Long id) {

        iCartRepository.deleteById(id);
    }

    @Override
    public ResponseCart updateCart(Long cartId, Integer quantity) {
        Cart nowCart = iCartRepository.findById(cartId).get();
        nowCart.setQuantity(quantity);
        Cart updatedCart = iCartRepository.save(nowCart);
        return ResponseCart.builder()
                .user(updatedCart.getUser())
                .quantity(updatedCart.getQuantity())
                .product(updatedCart.getProduct())
                .build();
    }


}
