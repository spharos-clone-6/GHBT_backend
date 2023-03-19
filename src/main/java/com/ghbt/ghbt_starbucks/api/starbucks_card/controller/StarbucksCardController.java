package com.ghbt.ghbt_starbucks.api.starbucks_card.controller;

import com.ghbt.ghbt_starbucks.api.starbucks_card.dto.RequestEnrollStarbucksCard;
import com.ghbt.ghbt_starbucks.api.starbucks_card.dto.ResponseStarbucksCard;
import com.ghbt.ghbt_starbucks.api.starbucks_card.service.IStarbucksCardService;
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
 * 스타벅스 카드 구매를 위해 전체 조회를 할 수 있는 API 입니다. 1. 전체 조회 2. 단건 조회 3. 모바일 카드 등록(관리자)
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/starbucks-card")
public class StarbucksCardController {

    private final IStarbucksCardService StarbucksCardServiceImpl;

    @GetMapping
    public ResponseEntity<Result> allMobileCard() {
        List<ResponseStarbucksCard> starbucksCards = StarbucksCardServiceImpl.getAllStarbucksCard();
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result(starbucksCards));
    }

    @GetMapping("/{starbucksCardId}")
    public ResponseEntity<ResponseStarbucksCard> oneMobileCard(@PathVariable Long starbucksCardId) {
        ResponseStarbucksCard starbucksCard = StarbucksCardServiceImpl.getOneStarbucksCard(starbucksCardId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(starbucksCard);
    }

    @PostMapping
    public ResponseEntity<?> saveMobileCardByAdmin(@RequestBody RequestEnrollStarbucksCard requestEnrollStarbucksCard) {
        StarbucksCardServiceImpl.enrollStarbucksCard(requestEnrollStarbucksCard);
        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @Data
    @AllArgsConstructor
    static class Result {

        private Object starbucksCards;
    }
}
