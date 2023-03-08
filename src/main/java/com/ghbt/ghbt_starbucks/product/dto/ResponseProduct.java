package com.ghbt.ghbt_starbucks.product.dto;


import com.ghbt.ghbt_starbucks.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String thumbnailUrl;
    private Integer stock;
    private Integer likeCount;
    private Boolean isBest;

    public static List<ResponseProduct> mapper(List<Product> products){
        List<ResponseProduct> responseProducts = new ArrayList<>();

        for (Product product: products) {
            responseProducts.add(ResponseProduct.builder()
                    .id(product.getId()).name(product.getName())
                    .price(product.getPrice()).description(product.getDescription())
                    .thumbnailUrl(product.getThumbnailUrl()).likeCount(product.getLikeCount())
                    .isBest(product.getIsBest()).stock(product.getStock()).build());
        }
        return responseProducts;
    }
}

