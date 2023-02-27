package com.ghbt.ghbt_starbucks.product.repository;

import com.ghbt.ghbt_starbucks.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
