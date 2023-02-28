package com.ghbt.ghbt_starbucks.purchase.controller;
import com.ghbt.ghbt_starbucks.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.purchase.service.IPurchaseService;
import com.ghbt.ghbt_starbucks.purchase.vo.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.vo.ResponsePurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor

public class PurchaseController {

    private final IPurchaseService iPurchaseService;

    @PostMapping("/add/{userId}")
    public void addPurchase(@RequestBody RequestPurchase requestPurchase, @PathVariable Long userId){
        Long purchasedId = iPurchaseService.addPurchase(requestPurchase, userId);
    }

    @GetMapping("/get/{id}")
    public ResponsePurchase getPurchaseById (@PathVariable Long id){
        return iPurchaseService.getPurchaseById(id);
    }

    @GetMapping("/my_purchase/{userId}")
    public List<Purchase> getAllPurchase(@PathVariable Long userId){
        return iPurchaseService.getAllPurchaseByUserId(userId);
    }

    @PutMapping("update/{id}")
    public ResponseEntity updatePurchase(@RequestBody RequestPurchase requestPurchase, @PathVariable Long id){
        iPurchaseService.updatePurchase(requestPurchase, id);

        return new ResponseEntity(HttpStatus.OK);
    }



}
