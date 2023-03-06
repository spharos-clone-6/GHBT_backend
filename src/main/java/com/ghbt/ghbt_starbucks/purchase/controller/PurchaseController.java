package com.ghbt.ghbt_starbucks.purchase.controller;

import com.ghbt.ghbt_starbucks.purchase.service.IPurchaseService;
import com.ghbt.ghbt_starbucks.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final IPurchaseService iPurchaseService;

    //상품 구매
    @PostMapping
    public ResponseEntity addPurchase(@RequestBody RequestPurchase requestPurchase, @LoginUser User loginUser) {
        Long purchasedId = iPurchaseService.addPurchase(requestPurchase, loginUser);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/{purchaseId}")
    public ResponsePurchase getPurchaseById(@PathVariable Long purchaseId) {
        return iPurchaseService.getPurchaseById(purchaseId);
    }

    @GetMapping
    public List<ResponsePurchase> getAllPurchase(@LoginUser User user) {
        return iPurchaseService.getAllPurchaseByUserId(user);
    }

    @PutMapping("/{purchaseId}")
    public ResponseEntity updatePurchase(@PathVariable Long purchaseId, @RequestBody RequestPurchase requestPurchase) {
        iPurchaseService.updatePurchase(requestPurchase, purchaseId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
