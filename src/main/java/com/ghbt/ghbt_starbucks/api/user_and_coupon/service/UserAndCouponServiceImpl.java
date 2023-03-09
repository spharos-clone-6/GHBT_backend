package com.ghbt.ghbt_starbucks.api.user_and_coupon.service;

import com.ghbt.ghbt_starbucks.api.coupon.model.Coupon;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.dto.RequestUserAndCoupon;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.dto.ResponseUserAndCoupon;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.model.UserAndCoupon;
import com.ghbt.ghbt_starbucks.api.user_and_coupon.repository.IUserAndCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAndCouponServiceImpl implements IUserAndCouponService {

    private final IUserAndCouponRepository iUserAndCouponRepository;

    @Override
    public Long addCoupon(RequestUserAndCoupon requestUserAndCoupon, User user) {
        UserAndCoupon userAndCoupon = UserAndCoupon.builder()
                .user(user)
                .couponUniqueNumber(requestUserAndCoupon.getCouponUniqueNumber())
                .build();
        UserAndCoupon saveUserAndCoupon = iUserAndCouponRepository.save(userAndCoupon);
        return saveUserAndCoupon.getId();
    }

    @Override
    public ResponseUserAndCoupon getCouponById(Long id) {
        UserAndCoupon userAndCoupon = iUserAndCouponRepository.findById(id).orElseThrow(() ->
                new ServiceException("요청하신 쿠폰은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        ResponseUserAndCoupon responseUserAndCoupon = ResponseUserAndCoupon.builder()
                .id(userAndCoupon.getId())
                .couponUniqueNumber(userAndCoupon.getCouponUniqueNumber())
                .build();

        return responseUserAndCoupon;
    }

    @Override
    public List<ResponseUserAndCoupon> getALLCouponByUserId() {
        return null;
    }

    @Override
    public void deleteCoupon(Long id) {

    }


}
