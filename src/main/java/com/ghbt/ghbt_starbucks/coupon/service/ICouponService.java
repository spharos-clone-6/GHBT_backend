package com.ghbt.ghbt_starbucks.coupon.service;

import com.ghbt.ghbt_starbucks.coupon.dto.RequestCoupon;
import com.ghbt.ghbt_starbucks.coupon.dto.ResponseCoupon;

public interface ICouponService {
    ResponseCoupon addCoupon(RequestCoupon requestCoupon);

}
