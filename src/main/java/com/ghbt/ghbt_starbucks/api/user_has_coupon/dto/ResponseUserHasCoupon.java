package com.ghbt.ghbt_starbucks.api.user_has_coupon.dto;

import com.ghbt.ghbt_starbucks.api.coupon.model.CouponType;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.model.UserHasCoupon;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserHasCoupon {

    private Long id;
    private String name;
    private Integer discountPrice;
    private CouponType couponType;
    private String pinNumber;
    private LocalDateTime createdDate;

    public static List<ResponseUserHasCoupon> mapper(List<UserHasCoupon> userHasCoupons) {
        List<ResponseUserHasCoupon> responseUserHasCoupons = new ArrayList<>();
        for (UserHasCoupon userHasCoupon : userHasCoupons) {
            responseUserHasCoupons.add(ResponseUserHasCoupon.builder()
                .id(userHasCoupon.getCoupon().getId())
                .name(userHasCoupon.getCoupon().getName())
                .couponType(userHasCoupon.getCoupon().getCouponType())
                .discountPrice(userHasCoupon.getCoupon().getDiscountPrice())
                .pinNumber(userHasCoupon.getCoupon().getPinNumber())
                .createdDate(userHasCoupon.getCreateDate())
                .build());
        }
        return responseUserHasCoupons;
    }
}
