package com.ghbt.ghbt_starbucks.api.user_and_coupon.controller;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.dto.RequestUserAndCoupon;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.service.IUserAndCouponService;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.dto.RequestUserAndCoupon;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.dto.ResponseUserAndCoupon;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.service.IUserAndCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "쿠폰", description = "쿠폰 관련 API")
@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class UserAndCouponController {

    private final IUserAndCouponService iUserAndCouponService;

    @Operation(summary = "쿠폰등록", description = "유저정보와 쿠폰 번호(string)를 입력합니다")
    @PostMapping
    public ResponseEntity addCoupon(@RequestBody RequestUserAndCoupon requestUserAndCoupon, @LoginUser User loginUser){
        Long couponId = iUserAndCouponService.addCoupon(requestUserAndCoupon, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseUserAndCoupon getUserCouponById(@PathVariable Long id, @LoginUser User loginUser){
        return iUserAndCouponService.getCouponById(id);
    }

}
