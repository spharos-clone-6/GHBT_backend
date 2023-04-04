package com.ghbt.ghbt_starbucks.global.error;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //로그인/회원가입
    NOT_LOGIN("로그인이 필요합니다.", UNAUTHORIZED, 401),
    NO_AUTHORIZATION("접근 권한이 없습니다.", FORBIDDEN, 403),
    INVALID_JWT("올바르지 않은 JWT 형식입니다", UNAUTHORIZED, 401),
    NOT_FOUND_USER("존재하지 않는 유저입니다.", UNAUTHORIZED, 401),

    //결제
    KAKAO_PAYMENT_CANCEL("결제를 진행하는 중 취소하였습니다.", BAD_REQUEST, 400),
    KAKAO_PAYMENT_FAIL("결제가 취소되었습니다.", BAD_REQUEST, 400),
    KAKAO_PAYMENT_SERVER_ERROR("카카오페이 결제 서버에 문제가 발생하였습니다.", INTERNAL_SERVER_ERROR, 500),
    NOT_FOUND_PAYMENT_TYPE("지원하지 않는 결제 방식입니다.", BAD_REQUEST, 400),
    OUT_OF_STOCK("재고가 부족합니다.", BAD_REQUEST, 400),
    NOT_MATCH_TOTAL("총 금액이 맞지 않습니다. 결제를 취소합니다.", INTERNAL_SERVER_ERROR, 500),
    FAIL_TO_SAVE_PAYMENT_INFORMATION("결제 정보 저장이 제대로 이루어지지 않았습니다.", INTERNAL_SERVER_ERROR, 500),

    //배송지
    NOT_FOUND_SHIPPING_ADDRESSES("등록된 배송지가 존재하지 않습니다.", NOT_FOUND, 404),
    SHIPPING_ADDRESS_DB_INVALID("배송지 기본 설정 로직에 이상이 생겼습니다.", INTERNAL_SERVER_ERROR, 500),

    //스타벅스카드
    NOT_FOUND_STARBUCKS_CARDS("해당 유저의 스타벅스 카드가 존재하지 않습니다.", NOT_FOUND, 404),
    WRONG_STARBUCKS_CARD_NUMBERS("유효하지 않는 등록번호입니다.", NOT_FOUND, 404),
    NOT_FOUND_STARBUCKS_CARD("등록된 모바일카드가 존재하지 않습니다.", NOT_FOUND, 404),

    //장바구니
    NOT_FOUND_CART("등록된 장바구니가 존재하지 않습니다.", NOT_FOUND, 404),

    //상품
    NOT_FOUND_PRODUCT("등록된 물품이 존재하지 않습니다.", NOT_FOUND, 404),
    PRODUCT_DISCONTINUATION("주문하신 상품은 판매가 중단되었습니다.", NOT_FOUND, 404),

    //카테고리
    NOT_FOUND_CATEGORY("등록된 카테고리가 존재하지 않습니다.", NOT_FOUND, 404),

    //쿠폰
    NOT_FOUND_COUPON("등록된 쿠폰이 존재하지 않습니다.", NOT_FOUND, 404),

    //이미지
    NOT_FOUND_IMAGE("등록된 이미지가 존재하지 않습니다.", NOT_FOUND, 404),
    ;

    private final String message;
    private final HttpStatus httpStatus;
    private final Integer errorCode;
}
