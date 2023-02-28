package com.ghbt.ghbt_starbucks.category.repository;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.product.model.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);





}
