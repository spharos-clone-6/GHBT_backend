package com.ghbt.ghbt_starbucks.api.user_has_coupon.service;

import com.ghbt.ghbt_starbucks.api.coupon.model.Coupon;
import com.ghbt.ghbt_starbucks.api.coupon.repeository.ICouponRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.dto.RequestUserHasCoupon;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.dto.ResponseUserHasCoupon;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.model.UserHasCoupon;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.repository.IUserHasCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHasCouponServiceImpl implements IUserHasCouponService {

    private final IUserHasCouponRepository iUserHasCouponRepository;

    private final ICouponRepository iCouponRepository;

    @Override
    public Long addCoupon(RequestUserHasCoupon requestUserHasCoupon, User user) {
        Coupon findCoupon = iCouponRepository.findAllByPinNumber(requestUserHasCoupon.getPinNumber())
            .orElseThrow(() -> new ServiceException("요청하신 쿠폰은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        UserHasCoupon userHasCoupon = UserHasCoupon.builder().user(user).coupon(findCoupon).build();
        return iUserHasCouponRepository.save(userHasCoupon).getId();
    }

    @Override
    public List<ResponseUserHasCoupon> getALLCouponByUserId(User user) {
        List<UserHasCoupon> userHasCouponList = iUserHasCouponRepository.findAllByUserId(user.getId());
        if (userHasCouponList.isEmpty()) {
            throw new ServiceException("등록된 쿠폰이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return ResponseUserHasCoupon.mapper(userHasCouponList);
    }

    @Override
    public ResponseUserHasCoupon getCoupon(User user, Long couponId) {
        UserHasCoupon userHasCouponList = iUserHasCouponRepository.findByUserIdAndCouponId(user.getId(), couponId)
            .orElseThrow(() -> new ServiceException("요청하신 쿠폰은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        return ResponseUserHasCoupon.builder().id(userHasCouponList.getId())
            .name(userHasCouponList.getCoupon().getName())
            .discountPrice(userHasCouponList.getCoupon().getDiscountPrice())
            .couponType(userHasCouponList.getCoupon().getCouponType())
            .pinNumber(userHasCouponList.getCoupon().getPinNumber()).createdDate(userHasCouponList.getCreateDate())
            .build();
    }

    @Override
    public void deleteCoupon(Long id) {
        iUserHasCouponRepository.deleteById(id);
    }
}
