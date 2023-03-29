package com.ghbt.ghbt_starbucks.api.kakaopay.dto;

import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
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
            .productName(
                (requestPurchase.getPurchaseList().size() == 1) ? requestPurchase.getPurchaseList().get(0).getProductName() :
                    requestPurchase.getPurchaseList().get(0).getProductName()
                        + " 외 "
                        + (requestPurchase.getPurchaseList().size() - 1)
                        + "건"
            )
            .productCount(String.valueOf(requestPurchase.getPurchaseList().size() - 1))
            .totalPrice(String.valueOf(requestPurchase.getTotalPrice()))
            .build();
    }
}

