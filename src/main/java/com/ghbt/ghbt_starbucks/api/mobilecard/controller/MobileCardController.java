package com.ghbt.ghbt_starbucks.api.mobilecard.controller;

import com.ghbt.ghbt_starbucks.api.mobilecard.dto.ResponseMobileCard;
import com.ghbt.ghbt_starbucks.api.mobilecard.service.MobileCardServiceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MobileCardController {

    private final MobileCardServiceImpl mobileCardService;

    @GetMapping
    public ResponseEntity<Result> allMobileCard() {
        List<ResponseMobileCard> mobileCards = mobileCardService.getAllMobileCard();
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result(mobileCards));
    }

    @GetMapping("/{mobileCardId}")
    public ResponseEntity<ResponseMobileCard> oneMobileCard(@PathVariable Long mobileCardId) {
        ResponseMobileCard mobileCard = mobileCardService.getOneMobileCard(mobileCardId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(mobileCard);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {

        private T shippingAddress;
    }
}
