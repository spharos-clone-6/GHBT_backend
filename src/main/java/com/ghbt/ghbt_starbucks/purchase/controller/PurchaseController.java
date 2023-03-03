package com.ghbt.ghbt_starbucks.purchase.controller;

import com.ghbt.ghbt_starbucks.purchase.service.IPurchaseService;
import com.ghbt.ghbt_starbucks.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final IPurchaseService iPurchaseService;
    private final IUserRepository iUserRepository;

    @PostMapping
    public void addPurchase(@RequestBody RequestPurchase requestPurchase,
                            Authentication authentication) {
        String email = authentication.getName();
        User user = iUserRepository.findByEmail(email).get();
        Long purchasedId = iPurchaseService.addPurchase(requestPurchase, user.getId());
    }

    @GetMapping("{purchase_id}")
    public ResponsePurchase getPurchaseById(@PathVariable("purchase_id") Long purchaseId) {
        return iPurchaseService.getPurchaseById(purchaseId);
    }

    @GetMapping
    public List<ResponsePurchase> getAllPurchase(Authentication authentication) {
        String email = authentication.getName();
        User user = iUserRepository.findByEmail(email).get();
        return iPurchaseService.getAllPurchaseByUserId(user.getId());
    }

    @PutMapping("{purchaseId}")
    public ResponseEntity updatePurchase(@PathVariable("purchaseId") Long purchaseId, @RequestBody RequestPurchase requestPurchase) {
        iPurchaseService.updatePurchase(requestPurchase, purchaseId);
        return new ResponseEntity(HttpStatus.OK);
    }


}
