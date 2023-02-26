package com.ghbt.ghbt_starbucks.product_and_category.vo;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.product.model.Product;
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
