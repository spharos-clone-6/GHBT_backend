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

    public KakaoPayOrderDto(RequestPurchase requestPurchase, UUID orderId, Long userId) {
        return KakaoPayOrderDto.builder()
            .orderId(orderId.toString())
            .memberId(userId.toString())
            .productName(requestPurchase.getProductName())
            .productCount(requestPurchase.getQuantity().toString())
            .totalPrice(requestPurchase.getTotalPrice.toString())
            .build();
    }
}

