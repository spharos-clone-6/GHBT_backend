package com.ghbt.ghbt_starbucks.product_and_category.dto;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.product.model.Product;
import lombok.Data;

@Data
public class RequestProductAndCategory {

    private Category category;
    private Product product;

}
