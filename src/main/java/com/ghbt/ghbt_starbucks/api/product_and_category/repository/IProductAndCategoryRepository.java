package com.ghbt.ghbt_starbucks.api.product_and_category.repository;

import com.ghbt.ghbt_starbucks.api.product_and_category.model.ProductAndCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductAndCategoryRepository extends JpaRepository<ProductAndCategory, Long> {

}
