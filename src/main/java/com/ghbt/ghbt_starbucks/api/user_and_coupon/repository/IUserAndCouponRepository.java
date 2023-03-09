package com.ghbt.ghbt_starbucks.api.user_and_coupon.repository;

import com.ghbt.ghbt_starbucks.api.user_and_coupon.model.UserAndCoupon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserAndCouponRepository extends JpaRepository<UserAndCoupon, Long> {

}
