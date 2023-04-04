package com.ghbt.ghbt_starbucks.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Boolean isNew;


}

