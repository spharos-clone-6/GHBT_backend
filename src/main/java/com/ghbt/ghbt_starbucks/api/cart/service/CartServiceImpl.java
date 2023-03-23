package com.ghbt.ghbt_starbucks.api.cart.service;

import com.ghbt.ghbt_starbucks.api.cart.model.Cart;
import com.ghbt.ghbt_starbucks.api.cart.repository.ICartRepository;
import com.ghbt.ghbt_starbucks.api.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.api.cart.vo.ResponseCart;

import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user.repository.IUserRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements ICartService {


    private final ICartRepository iCartRepository;
    private final IUserRepository iUserRepository;
    private final IProductRepository iProductRepository;

    @Override
    public void addCart(RequestCart requestCart, User loginUser) {

        //삭제된 cart_list 인지 확인
        Cart iscart = iCartRepository.findByUserIdAndProductId(loginUser.getId(), requestCart.getProductId());

        //있는경우
        if (iscart != null) {
            //소프트 딜리트 되어있는 경우
            if (iscart.getDeleted()) {
                Cart preCart = iCartRepository.findById(iscart.getId())
                    .orElseThrow(() -> new ServiceException("등록되어있는 장바구니가 없습니다..", HttpStatus.NO_CONTENT));
                preCart.setDeleted(false);
                preCart.setQuantity(requestCart.getQuantity());
                iCartRepository.save(preCart);
                return;
            }
            //이미 존재하는 경우
            iscart.setQuantity(iscart.getQuantity() + requestCart.getQuantity());
            iCartRepository.save(iscart);
            return;
        }
        Product product = iProductRepository.findById(requestCart.getProductId())
            .orElseThrow(() -> new ServiceException("등록되어있는 물품이 없습니다.", HttpStatus.NO_CONTENT));

        //장바구니에 등록되어 있는지 확인
        Cart cart = Cart.builder()
            .product(product)
            .user(loginUser)
            .quantity(requestCart.getQuantity())
            .build();
        iCartRepository.save(cart);
    }

    @Override
    public ResponseCart getCart(Long cartId) {
        Cart cart = iCartRepository.findById(cartId)
            .orElseThrow(() -> new ServiceException("등록된 장바구니가 없습니다.", HttpStatus.NO_CONTENT));

        return ResponseCart.builder()
            .id(cart.getId())
            .product(cart.getProduct())
            .quantity(cart.getQuantity())
            .user(cart.getUser())
            .checked(cart.getChecked())
            .build();
    }


    @Override
    public List<ResponseCart> getAllCartByUserId(Long userId) {
        List<Cart> carts = iCartRepository.findAllByUser_IdAndDeleted(userId);

        return carts
            .stream()
            .map(ResponseCart::from)
            .collect(Collectors.toList());

    }

    @Override
    public List<ResponseCart> getAllCartByUserIdAndIce(Long userId) {
        List<Cart> carts = iCartRepository.findAllByUser_IdAndIceAndDeleted(userId);

        return carts
            .stream()
            .map(ResponseCart::from)
            .collect(Collectors.toList());

    }

    @Override
    public void deleteCart(Long id) {
        iCartRepository.deleteById(id);
    }

    @Override
    public ResponseCart updateCart(Long cartId, Integer quantity) {
        Cart nowCart = iCartRepository.findByCartId(cartId)
            .orElseThrow(() -> new ServiceException("등록된 장바구니가 없습니다.", HttpStatus.NO_CONTENT));

        nowCart.updateQuantity(quantity);
        iCartRepository.save(nowCart);

        return ResponseCart.builder()
            .user(nowCart.getUser())
            .product(nowCart.getProduct())
            .deleted(nowCart.getDeleted())
            .quantity(nowCart.getQuantity())
            .checked(nowCart.getChecked())
            .id(nowCart.getId())
            .build();
    }
}
