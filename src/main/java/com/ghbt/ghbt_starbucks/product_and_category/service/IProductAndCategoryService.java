package com.ghbt.ghbt_starbucks.product_and_category.service;

import com.ghbt.ghbt_starbucks.product_and_category.vo.RequestProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.vo.ResponseProductAndCategory;

public interface IProductAndCategoryService {
    ResponseProductAndCategory addProductAndCategory(RequestProductAndCategory requestProductAndCategoryq);
    ResponseProductAndCategory getProductAndCategory(Long id);
}
