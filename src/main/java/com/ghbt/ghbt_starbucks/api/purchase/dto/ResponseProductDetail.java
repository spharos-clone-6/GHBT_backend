package com.ghbt.ghbt_starbucks.api.purchase.dto;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDetail {

    private Long productId;
    private String productName;
    private Long productQuantity;
    private Integer productPrice;
    private String productThumbnail;

    public static ResponseProductDetail from(OrderProductDto orderProductDto) {
        return ResponseProductDetail.builder()
            .productId(orderProductDto.getProduct().getId())
            .productName(orderProductDto.getProduct().getName())
            .productQuantity(orderProductDto.getProductQuantity())
            .productPrice(orderProductDto.getProduct().getPrice())
            .productThumbnail(orderProductDto.getProduct().getThumbnailUrl())
            .build();
    }
}
