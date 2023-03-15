package com.ghbt.ghbt_starbucks.api.user_has_coupon.service;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.dto.RequestUserHasCoupon;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.dto.ResponseUserHasCoupon;

import java.util.List;

public interface IUserHasCouponService {

    Long addCoupon(RequestUserHasCoupon requestUserHasCoupon, User user);

    ResponseUserHasCoupon getCoupon(User user, Long couponId);

    List<ResponseUserHasCoupon> getALLCouponByUserId(User user);

    void deleteCoupon(Long id);
}
