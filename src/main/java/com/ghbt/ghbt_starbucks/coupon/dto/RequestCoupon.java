package com.ghbt.ghbt_starbucks.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestCoupon {

    private String name;
    private Integer discountPrice;
    private Boolean isFree;
}
