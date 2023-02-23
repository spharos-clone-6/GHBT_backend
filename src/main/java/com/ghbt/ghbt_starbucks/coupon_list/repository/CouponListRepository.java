package com.ghbt.ghbt_starbucks.coupon_list.repository;

import com.ghbt.ghbt_starbucks.coupon_list.model.CouponList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponListRepository extends JpaRepository<CouponList, Long> {
}
