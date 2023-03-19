package com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.controller;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.RequestChargeStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.RequestStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.ResponseStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.service.IUserHasStarbucksCardService;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-starbucks-card")
@RequiredArgsConstructor
public class UserHasStarbucksCardController {

    private final IUserHasStarbucksCardService userHasStarbucksServiceImpl;

    @GetMapping
    public ResponseEntity<Result> userMobileCards(@LoginUser User loginUser) {
        List<ResponseStarbucksCard> userStarbucksCards = userHasStarbucksServiceImpl.getUserStarbucksCards(
            loginUser.getId());
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result(userStarbucksCards));
    }

    @GetMapping("/{starbucksCardId}")
    public ResponseEntity<ResponseStarbucksCard> userMobileCard(@LoginUser User loginUser,
        @PathVariable Long starbucksCardId) {
        ResponseStarbucksCard userStarbucksCard = userHasStarbucksServiceImpl.getUserStarbucksCard(loginUser.getId(),
            starbucksCardId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(userStarbucksCard);
    }

    @PostMapping
    public ResponseEntity<?> enrollMobileCard(@LoginUser User loginUser,
        @RequestBody RequestStarbucksCard requestStarbucksCard) {
        userHasStarbucksServiceImpl.saveUserStarbucksCard(loginUser, requestStarbucksCard);
        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @PatchMapping("/{starbucksCardId}")
    public ResponseEntity<Result> chargeStarbucksCard(
        @LoginUser User loginUser,
        @PathVariable Long starbucksCardId,
        @RequestBody RequestChargeStarbucksCard requestChargeStarbucksCard) {
        ResponseStarbucksCard responseStarbucksCard = userHasStarbucksServiceImpl.chargeStarbucksCard(
            loginUser.getId(),
            starbucksCardId, requestChargeStarbucksCard);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result(responseStarbucksCard));
    }

    @Data
    @AllArgsConstructor
    static class Result {

        private Object starbucksCard;
    }
}
