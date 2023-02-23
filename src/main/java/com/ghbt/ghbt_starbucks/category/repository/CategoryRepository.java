package com.ghbt.ghbt_starbucks.category.repository;

import com.ghbt.ghbt_starbucks.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
