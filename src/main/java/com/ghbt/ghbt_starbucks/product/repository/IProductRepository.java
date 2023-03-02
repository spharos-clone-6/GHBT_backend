package com.ghbt.ghbt_starbucks.product.repository;

import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.productDto.ProductDto;
import com.ghbt.ghbt_starbucks.product.productDto.ProductListByCategory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p.id AS id, p.name AS name, p.price AS price from product_and_category pac left join category c on pac.category_id =c.id left join product p on pac.product_id  = p.id where c.name = :search",nativeQuery = true)
    List<ProductListByCategory> findAllProductType (@Param("search") String search);
}
