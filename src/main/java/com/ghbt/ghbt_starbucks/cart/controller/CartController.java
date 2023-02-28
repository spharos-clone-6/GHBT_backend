package com.ghbt.ghbt_starbucks.cart.controller;


import com.ghbt.ghbt_starbucks.cart.service.ICartService;
import com.ghbt.ghbt_starbucks.cart.vo.RequestCart;
import com.ghbt.ghbt_starbucks.cart.vo.ResponseCart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final ICartService iCartService;

    @PostMapping()
    public void addCart(@RequestBody RequestCart requestCart){
        iCartService.addCart(requestCart);
    }

    @GetMapping("/my_cart/{id}")
    public List<ResponseCart> responseCart(@PathVariable Long id){
        return iCartService.getAllCartByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseCart getResponseCart(@PathVariable Long id){
        return iCartService.getCart(id);
    }
}
