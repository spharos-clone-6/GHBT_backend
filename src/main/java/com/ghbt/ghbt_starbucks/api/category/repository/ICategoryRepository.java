package com.ghbt.ghbt_starbucks.api.category.repository;

import com.ghbt.ghbt_starbucks.api.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
