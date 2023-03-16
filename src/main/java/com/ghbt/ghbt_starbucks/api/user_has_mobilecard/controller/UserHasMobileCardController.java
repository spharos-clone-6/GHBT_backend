package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.controller;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.ResponseMobileCardAndUser;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.service.IUserHasMobileCardService;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
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

@RestController
@RequestMapping("/api/own-mobile-cards")
@RequiredArgsConstructor
public class UserHasMobileCardController {

    private final IUserHasMobileCardService userHasMobileCardServiceImpl;

    @GetMapping
    public ResponseEntity<Result> userMobileCards(@LoginUser User loginUser) {
        List<ResponseMobileCardAndUser> userMobileCards = userHasMobileCardServiceImpl.getUserMobileCards(
            loginUser.getId());
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result(userMobileCards));
    }

    @GetMapping("/{mobileCardId}")
    public ResponseEntity<ResponseMobileCardAndUser> userMobileCard(@LoginUser User loginUser,
        @PathVariable Long mobileCardId) {
        ResponseMobileCardAndUser userMobileCard = userHasMobileCardServiceImpl.getUserMobileCard(loginUser.getId(),
            mobileCardId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(userMobileCard);
    }

    @PostMapping
    public ResponseEntity<?> enrollMobileCard(@LoginUser User loginUser,
        @RequestBody RequestMobileCard requestMobileCard) {
        userHasMobileCardServiceImpl.saveUserMobileCard(loginUser, requestMobileCard);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Data
    @AllArgsConstructor
    static class Result {

        private Object mobileCard;
    }
}
