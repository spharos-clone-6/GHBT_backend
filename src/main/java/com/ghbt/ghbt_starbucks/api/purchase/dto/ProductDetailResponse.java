package com.ghbt.ghbt_starbucks.api.purchase.dto;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.OrderProductDto;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {

    private Long productId;
    private String productName;
    private Long productQuantity;
    private Integer productPrice;
    private String productThumbnail;

    public static ProductDetailResponse from(OrderProductDto orderProductDto) {
        return ProductDetailResponse.builder()
            .productId(orderProductDto.getProduct().getId())
            .productName(orderProductDto.getProduct().getName())
            .productQuantity(orderProductDto.getProductQuantity())
            .productPrice(orderProductDto.getProduct().getPrice())
            .productThumbnail(orderProductDto.getProduct().getThumbnailUrl())
            .build();
    }
}
