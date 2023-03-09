package com.ghbt.ghbt_starbucks.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProduct {

    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String thumbnailUrl;
    private Integer stock;
    private Integer likeCount;
    private Boolean isBest;
    private Boolean isNew;
    private List<String> categoryList;

}
