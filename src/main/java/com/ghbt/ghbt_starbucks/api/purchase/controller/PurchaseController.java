package com.ghbt.ghbt_starbucks.api.purchase.controller;

import com.ghbt.ghbt_starbucks.api.purchase.service.IPurchaseService;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name ="구매", description = "구매 관련 API")
@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseController {
    private final IPurchaseService iPurchaseService;

    //상품 구매
    @Operation(summary = "구매하기(추가)", description = "productId(str), productName(str), price(int), quantity(int)," +
            " purchaseGgroup(Str), shippingAddress(str), shippingStatus(enum){SHIPPED, IN_DELIVERY, DELIVERED}, address(str) 로 입력해주세요")
    @PostMapping
    public ResponseEntity addPurchase(@RequestBody RequestPurchase requestPurchase, @LoginUser User loginUser) {
        Long purchasedId = iPurchaseService.addPurchase(requestPurchase, loginUser);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Operation(summary = "구매내역 조회(단건)", description = "purchaseId를 long으로 입력해주세요")

    @Parameters({
            @Parameter(name = "purchaseId", description = "구매내역 고유 번호", example = "1")
    })
    @GetMapping("/{purchaseId}")
    public ResponsePurchase getPurchaseById(@PathVariable Long purchaseId) {
        return iPurchaseService.getPurchaseById(purchaseId);
    }

    @Operation(summary = "구매내역 조회(모두)", description = "유저데이터를 기반으로 모든 구매내역을 조회합니다.")
    @GetMapping
    public List<ResponsePurchase> getAllPurchase(@LoginUser User user) {
        return iPurchaseService.getAllPurchaseByUserId(user);
    }

    @Operation(summary = "배송지 변경", description = "배송지를 변경합니다. RequestBody에서 배송지와 parameter로 주문번호를 Long 으로 입력해주세요")
    @PutMapping("/{purchaseId}")
    public ResponseEntity updatePurchase(@PathVariable Long purchaseId, @RequestBody RequestPurchase requestPurchase) {
        iPurchaseService.updatePurchase(requestPurchase, purchaseId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

