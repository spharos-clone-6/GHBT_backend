package com.ghbt.ghbt_starbucks.api.product_and_category.repository;

import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product_and_category.model.ProductAndCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductAndCategoryRepository extends JpaRepository<ProductAndCategory, Long> {

    List<ProductAndCategory> findByProductId(Product product);
}
