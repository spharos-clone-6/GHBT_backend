package com.ghbt.ghbt_starbucks.api.cart.controller;


import com.ghbt.ghbt_starbucks.api.cart.service.ICartService;
import com.ghbt.ghbt_starbucks.api.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.api.cart.vo.ResponseCart;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.api.user.model.User;
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
    public void addCart(@RequestBody RequestCart requestCart, @LoginUser User loginUser) {
        iCartService.addCart(requestCart, loginUser);
    }

    @GetMapping("/my_cart")
    @Operation(summary = "내가 담은 장바구니들 찾기", description = "로그인한 유저만 가능!!!")
    public List<ResponseCart> responseCart(@LoginUser User loginUser) {
        Long id = loginUser.getId();
        return iCartService.getAllCartByUserId(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "장바구니 상품 아이디값으로 검색", description = "장바구니 cart_id 입력해주세요")
    public ResponseCart getResponseCart(@PathVariable Long id) {
        return iCartService.getCart(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니 cart_id 입력해주세요")
    public void deleteCart(@PathVariable Long id) {
        iCartService.deleteCart(id);
    }

    @PutMapping("/{id}/{quantity}")
    @Operation(summary = "장바구니 상품 개수 업데이트", description = "장바구니 id 값과 개수 quantity를 입력해주세요")
    public ResponseCart updateResponseCart(@PathVariable Long id, @PathVariable Integer quantity) {
        return iCartService.updateCart(id, quantity);
    }
}
