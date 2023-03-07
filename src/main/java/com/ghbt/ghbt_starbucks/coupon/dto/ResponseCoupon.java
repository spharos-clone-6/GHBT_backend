package com.ghbt.ghbt_starbucks.coupon.dto;

import com.ghbt.ghbt_starbucks.coupon.model.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ResponseCoupon {

    private Long id;
    private String name;

    private Integer discountPrice;

    private Boolean isFree;

    public static ResponseCoupon from(Coupon coupon){
        return ResponseCoupon.builder()
                .id(coupon.getId())
                .name(coupon.getName())
                .discountPrice(coupon.getDiscountPrice())
                .isFree(coupon.getIsFree())
                .build();
    }

}
