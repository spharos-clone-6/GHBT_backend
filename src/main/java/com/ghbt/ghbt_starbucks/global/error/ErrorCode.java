package com.ghbt.ghbt_starbucks.global.error;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_LOGIN("로그인이 필요합니다.", UNAUTHORIZED, 401),
    NO_AUTHORIZATION("접근 권한이 없습니다.", FORBIDDEN, 403),
    INVALID_JWT("올바르지 않은 JWT 형식입니다", UNAUTHORIZED, 401),
    NOT_FOUND_USER("존재하지 않는 유저입니다.", UNAUTHORIZED, 401),

    NOT_FOUND_SHIPPING_ADDRESSES("등록된 배송지가 존재하지 않습니다.", NOT_FOUND, 404),
    SHIPPING_ADDRESS_DB_INVALID("배송지 기본 설정 로직에 이상이 생겼습니다.", INTERNAL_SERVER_ERROR, 500),

    NOT_FOUND_STARBUCKS_CARDS("해당 유저의 스타벅스 카드가 존재하지 않습니다.", NOT_FOUND, 404),
    WRONG_STARBUCKS_CARD_NUMBERS("유효하지 않는 등록번호입니다.", NOT_FOUND, 404),

    NOT_FOUND_CART("등록된 장바구니가 존재하지 않습니다.", NOT_FOUND, 404),

    NOT_FOUND_PRODUCT("등록된 물품이 존재하지 않습니다.", NOT_FOUND, 404),

    NOT_FOUND_CATEGORY("등록된 카테고리가 존재하지 않습니다.", NOT_FOUND, 404),

    NOT_FOUND_COUPON("등록된 쿠폰이 존재하지 않습니다.", NOT_FOUND, 404),

    NOT_FOUND_IMAGE("등록된 이미지가 존재하지 않습니다.", NOT_FOUND, 404),

    NOT_FOUND_MOBILE_CARD("등록된 모바일카드가 존재하지 않습니다.", NOT_FOUND, 404),
    ;

    private final String message;
    private final HttpStatus httpStatus;
    private final Integer errorCode;
}
