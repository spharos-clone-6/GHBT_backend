package com.ghbt.ghbt_starbucks.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_SHIPPING_ADDRESSES("등록된 배송지가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_CART("등록된 장바구니가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_PRODUCT("등록된 물품이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_CATEGORY("등록된 카테고리가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_COUPON("등록된 쿠폰이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_IMAGE("등록된 이미지가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_MOBILE_CARD("등록된 모바일카드가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;

    private String message;
    private HttpStatus httpStatus;
}
