package com.ghbt.ghbt_starbucks.coupon.controller;

import com.ghbt.ghbt_starbucks.coupon.dto.RequestCoupon;
import com.ghbt.ghbt_starbucks.coupon.dto.ResponseCoupon;
import com.ghbt.ghbt_starbucks.coupon.service.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final ICouponService iCouponService;

    @PostMapping
    public ResponseCoupon addCoupon(@RequestBody RequestCoupon requestCoupon){
        return iCouponService.addCoupon(requestCoupon);
    }
}
