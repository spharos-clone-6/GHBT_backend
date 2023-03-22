package com.ghbt.ghbt_starbucks.api.product.dto;


import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

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

