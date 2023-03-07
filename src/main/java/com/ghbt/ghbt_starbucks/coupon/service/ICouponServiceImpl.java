package com.ghbt.ghbt_starbucks.coupon.service;

import com.ghbt.ghbt_starbucks.coupon.dto.RequestCoupon;
import com.ghbt.ghbt_starbucks.coupon.dto.ResponseCoupon;
import com.ghbt.ghbt_starbucks.coupon.model.Coupon;
import com.ghbt.ghbt_starbucks.coupon.repository.ICouponRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor

public class ICouponServiceImpl implements ICouponService {

    private final ICouponRepository iCouponRepository;
    @Override
    public ResponseCoupon addCoupon(RequestCoupon requestCoupon) {
        Coupon coupon = Coupon.builder()
                .name(requestCoupon.getName())
                .discountPrice(requestCoupon.getDiscountPrice())
                .isFree(requestCoupon.getIsFree())
                .build();
        Coupon savedCoupon = iCouponRepository.save(coupon);

        ResponseCoupon responseCoupon = ResponseCoupon.builder()
                .id(savedCoupon.getId())
                .name(savedCoupon.getName())
                .discountPrice(savedCoupon.getDiscountPrice())
                .isFree(savedCoupon.getIsFree())
                .build();

        return responseCoupon;
    }
}
