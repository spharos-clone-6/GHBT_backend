package com.ghbt.ghbt_starbucks.api.product_and_category.dto;

import com.ghbt.ghbt_starbucks.api.category.model.Category;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductAndCategory {
    private Long id;
    private Category categoryId;
    private Product productId;
}
