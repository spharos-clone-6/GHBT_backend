package com.ghbt.ghbt_starbucks.purchase.controller;


import com.ghbt.ghbt_starbucks.purchase.service.IPurchaseService;
import com.ghbt.ghbt_starbucks.purchase.vo.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.vo.ResponsePurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor

public class PurchaseController {

    private final IPurchaseService iPurchaseService;

    @PostMapping("/add/{userId}")
    public void addPurchase(@RequestBody RequestPurchase requestPurchase, @PathVariable Long userId){
        System.out.println("userId = " + userId);
        Long purchasedId = iPurchaseService.addPurchase(requestPurchase, userId);
        System.out.println("purchasedId = " + purchasedId);

    }



}
