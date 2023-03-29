package com.ghbt.ghbt_starbucks.api.kakaopay.dto;

import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchases;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPayOrderDto {

    private String orderId;
    private String memberId;
    private String productName;
    private String productCount;
    private String totalPrice;

    public static KakaoPayOrderDto toKakaoOrder(RequestPurchase requestPurchase, UUID orderId, Long userId) {
        return KakaoPayOrderDto.builder()
            .orderId(orderId.toString())
            .memberId(userId.toString())
            .productName(requestPurchase.getProductName())
            .productCount(requestPurchase.getProductQuantity().toString())
            .totalPrice(requestPurchase.getTotalPrice().toString())
            .build();
    }

    public static KakaoPayOrderDto toKakaoOrderFromCart(RequestPurchases requestPurchases, UUID orderId, Long userId) {

//   totalprice 구하는 방식 fix되면 삭제 예정
//        long allProductPrice = 0;
//        for (int i = 0; i < requestPurchases.getPurchaseList().size(); i++) {
//            allProductPrice +=
//                requestPurchases.getPurchaseList().get(i).getProductPrice() * requestPurchases.getPurchaseList().get(i)
//                    .getProductQuantity();
//        }

        return KakaoPayOrderDto.builder()
            .orderId(orderId.toString())
            .memberId(userId.toString())
            .productName(requestPurchases.getPurchaseList().get(0).getProductName() + " 외 " + (
                requestPurchases.getPurchaseList().size() - 1) + "건")
            .productCount(String.valueOf(requestPurchases.getPurchaseList().size() - 1))
            .totalPrice(String.valueOf(requestPurchases.getTotalPrice()))
            .build();
    }
}

