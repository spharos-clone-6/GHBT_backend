package com.ghbt.ghbt_starbucks.api.user_and_coupon.dto;

import com.ghbt.ghbt_starbucks.api.coupon.model.Coupon;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserAndCoupon {

    private Long id;

    private User user;

    private String couponUniqueNumber;

    private Coupon coupon;

}
