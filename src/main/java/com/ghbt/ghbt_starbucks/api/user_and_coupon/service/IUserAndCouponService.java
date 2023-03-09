package com.ghbt.ghbt_starbucks.api.user_and_coupon.service;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.dto.RequestUserAndCoupon;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.dto.ResponseUserAndCoupon;

import java.util.List;

public interface IUserAndCouponService {
    Long addCoupon(RequestUserAndCoupon requestUserAndCoupon, User user);
    ResponseUserAndCoupon getCouponById(Long id);
    List<ResponseUserAndCoupon> getALLCouponByUserId();
    void deleteCoupon(Long id);


}
