package com.ghbt.ghbt_starbucks.api.kakaopay.dto;

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

    public static KakaoPayOrderDto toKakaoOrder(RequestPurchases requestPurchases, UUID orderId, Long userId) {

        return KakaoPayOrderDto.builder()
            .orderId(orderId.toString())
            .memberId(userId.toString())
            .productName(
                (requestPurchases.getPurchaseList().size() == 1) ? requestPurchases.getPurchaseList().get(0).getProductName() :
                    requestPurchases.getPurchaseList().get(0).getProductName()
                        + " 외 "
                        + (requestPurchases.getPurchaseList().size() - 1)
                        + "건"
            )
            .productCount(String.valueOf(requestPurchases.getPurchaseList().size() - 1))
            .totalPrice(String.valueOf(requestPurchases.getTotalPrice()))
            .build();
    }
}

