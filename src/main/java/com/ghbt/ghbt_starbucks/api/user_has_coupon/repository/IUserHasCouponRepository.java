package com.ghbt.ghbt_starbucks.api.user_has_coupon.repository;

import com.ghbt.ghbt_starbucks.api.user_has_coupon.model.UserHasCoupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IUserHasCouponRepository extends JpaRepository<UserHasCoupon, Long> {

    @Query("select uhc from UserHasCoupon uhc join fetch uhc.user u join fetch uhc.coupon c where u.id =:userId")
    List<UserHasCoupon> findAllByUserId(@Param("userId") Long userId);

    @Query("select uhc from UserHasCoupon uhc join fetch uhc.user u join fetch uhc.coupon c where u.id =:userId and c.id=:couponId")
    Optional<UserHasCoupon> findByUserIdAndCouponId(@Param("userId") Long userId, @Param("couponId") Long couponId);
}
