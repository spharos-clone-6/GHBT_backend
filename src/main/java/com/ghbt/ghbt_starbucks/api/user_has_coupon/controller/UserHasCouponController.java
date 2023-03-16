package com.ghbt.ghbt_starbucks.api.user_has_coupon.controller;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.dto.RequestUserHasCoupon;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.service.IUserHasCouponService;
import com.ghbt.ghbt_starbucks.global.security.annotation.LoginUser;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.dto.ResponseUserHasCoupon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "쿠폰", description = "쿠폰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
@CrossOrigin(origins = "http://localhost:3000")

public class UserHasCouponController {

    private final IUserHasCouponService iUserHasCouponService;

    @Operation(summary = "쿠폰등록", description = "유저정보와 쿠폰 번호(string)를 입  력합니다")
    @PostMapping
    public ResponseEntity<Object> addCoupon(@RequestBody RequestUserHasCoupon requestUserHasCoupon,
        @LoginUser User loginUser) {
        iUserHasCouponService.addCoupon(requestUserHasCoupon, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseUserHasCoupon getUserCouponById(@LoginUser User loginUser, @PathVariable Long id) {
        return iUserHasCouponService.getCoupon(loginUser, id);
    }

    @GetMapping("/view")
    public List<ResponseUserHasCoupon> getAllCouponByUserId(@LoginUser User user) {
        return iUserHasCouponService.getALLCouponByUserId(user);
    }

    @DeleteMapping("/{id}")
    public void deleteCouponById(@PathVariable Long id) {
        iUserHasCouponService.deleteCoupon(id);
    }
}
