package com.ghbt.ghbt_starbucks.coupon.repository;

import com.ghbt.ghbt_starbucks.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICouponRepository extends JpaRepository<Coupon, Long> {
}
