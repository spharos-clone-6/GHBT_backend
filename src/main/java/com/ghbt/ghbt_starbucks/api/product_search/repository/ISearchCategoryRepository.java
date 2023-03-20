package com.ghbt.ghbt_starbucks.api.product_search.repository;

import com.ghbt.ghbt_starbucks.api.product_search.model.SearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISearchCategoryRepository extends JpaRepository<SearchCategory, Long> {

}
