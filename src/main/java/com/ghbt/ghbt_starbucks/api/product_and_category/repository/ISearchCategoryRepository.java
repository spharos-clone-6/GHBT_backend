package com.ghbt.ghbt_starbucks.api.product_and_category.repository;

import com.ghbt.ghbt_starbucks.api.product_and_category.model.SearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISearchCategoryRepository extends JpaRepository<SearchCategory, Long> {

}
