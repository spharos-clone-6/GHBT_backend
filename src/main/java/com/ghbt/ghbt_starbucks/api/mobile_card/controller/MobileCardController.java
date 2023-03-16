package com.ghbt.ghbt_starbucks.api.mobile_card.controller;

import com.ghbt.ghbt_starbucks.api.mobile_card.dto.RequestMobileCardToEnroll;
import com.ghbt.ghbt_starbucks.api.mobile_card.dto.ResponseMobileCard;
import com.ghbt.ghbt_starbucks.api.mobile_card.service.IMobileCardService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 모바일 카드 구매를 위해 전체 조회를 할 수 있는 API 입니다. 1. 전체 조회 2. 단건 조회 3. 모바일 카드 등록(관리자)
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mobile-card")
public class MobileCardController {

    private final IMobileCardService mobileCardServiceImpl;

    @GetMapping
    public ResponseEntity<Result> allMobileCard() {
        List<ResponseMobileCard> mobileCards = mobileCardServiceImpl.getAllMobileCard();
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result(mobileCards));
    }

    @GetMapping("/{mobileCardId}")
    public ResponseEntity<ResponseMobileCard> oneMobileCard(@PathVariable Long mobileCardId) {
        ResponseMobileCard mobileCard = mobileCardServiceImpl.getOneMobileCard(mobileCardId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(mobileCard);
    }

    @PostMapping
    public ResponseEntity<?> saveMobileCardByAdmin(@RequestBody RequestMobileCardToEnroll requestMobileCardToEnroll) {
        mobileCardServiceImpl.enrollMobileCard(requestMobileCardToEnroll);
        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @Data
    @AllArgsConstructor
    static class Result {

        private Object mobileCard;
    }
}
