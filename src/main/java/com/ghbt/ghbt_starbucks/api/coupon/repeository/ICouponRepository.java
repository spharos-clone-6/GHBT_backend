package com.ghbt.ghbt_starbucks.api.coupon.repeository;

import com.ghbt.ghbt_starbucks.api.coupon.model.Coupon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findAllByPinNumber(String pinNumber);
}
