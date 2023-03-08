package com.ghbt.ghbt_starbucks.cart.controller;


import com.ghbt.ghbt_starbucks.cart.service.ICartService;
import com.ghbt.ghbt_starbucks.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.cart.vo.ResponseCart;
import com.ghbt.ghbt_starbucks.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final ICartService iCartService;

    @PostMapping()
    @Operation(summary = "장바구니 담기", description = " productid(long) 수량(integer) 입력해주세요 !!로그인 유저만 가능")
    public void addCart(@RequestBody RequestCart requestCart, @LoginUser User loginUser){
        iCartService.addCart(requestCart,loginUser);
    }

    @GetMapping("/my_cart/{id}")
    public List<ResponseCart> responseCart(@PathVariable Long id){
        return iCartService.getAllCartByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseCart getResponseCart(@PathVariable Long id){
        return iCartService.getCart(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id){
        iCartService.deleteCart(id);
    }

    @PutMapping("/{id}/{quantity}")
    public ResponseCart updateResponseCart(@PathVariable Long id, @PathVariable Integer quantity){
        return iCartService.updateCart(id,quantity);
    }
}
