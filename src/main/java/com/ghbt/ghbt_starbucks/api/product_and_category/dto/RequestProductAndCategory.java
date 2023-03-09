package com.ghbt.ghbt_starbucks.api.product_and_category.dto;

import com.ghbt.ghbt_starbucks.api.category.model.Category;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import lombok.Data;

@Data
public class RequestProductAndCategory {

    private Category category;
    private Product product;

}
