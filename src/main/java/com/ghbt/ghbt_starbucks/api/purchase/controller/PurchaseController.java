package com.ghbt.ghbt_starbucks.api.purchase.controller;

import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPayResult;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePayment;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.api.purchase.service.PurchaseServiceImpl;
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

@Tag(name = "구매", description = "구매 관련 API")
@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseController {

    private final PurchaseServiceImpl iPurchaseService;

    @Operation(summary = "구매하기(장바구니", description = "장바구니를 통한 구매")
    @PostMapping
    public ResponseEntity<Object> startPurchase(@RequestBody RequestPurchase requestPurchase,
        @LoginUser User loginUser) {
        ResponsePayment responseReady = iPurchaseService.startPayment(requestPurchase, loginUser);
        return ResponseEntity.status(HttpStatus.OK)
            .body(responseReady);
    }

    @PostMapping("/end")
    public ResponseEntity<?> endPurchase(@LoginUser User loginUser) {
        iPurchaseService.endPayment(loginUser);
        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelPurchase(@LoginUser User loginUser) {
        iPurchaseService.cancelPurchase(loginUser);
        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @Operation(summary = "구매내역 조회(단건)", description = "purchaseId를 long 으로 입력해주세요")
    @Parameters({@Parameter(name = "purchaseId", description = "구매내역 고유 번호", example = "1")})
    @GetMapping("/{purchaseId}")
    public ResponsePurchase getPurchaseById(@PathVariable Long purchaseId) {

        return iPurchaseService.getPurchaseById(purchaseId);
    }

    @Operation(summary = "구매내역 조회(모두)", description = "유저데이터를 기반으로 모든 구매내역을 조회합니다.")
    @GetMapping
    public List<ResponsePurchase> getAllPurchase(@LoginUser User user) {
        return iPurchaseService.getAllPurchaseByUserId(user);
    }

    @Operation(summary = "청구서 출력", description = "유저정보로 배송지, 쿠폰, 카드를 출력")
    @GetMapping("/bill")
    public ResponseBill addBill(@LoginUser User user) {
        return iPurchaseService.getBill(user);
    }

    @Operation(summary = "프로세스 업데이트", description = "결제 완료시 processStatus값 업데이트")
    @PutMapping
    public void updateProcess(@RequestBody RequestPayResult requestPayResult, @LoginUser User user) {
        iPurchaseService.updateProcess(requestPayResult, user);
    }
}

