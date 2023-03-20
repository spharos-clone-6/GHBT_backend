package com.ghbt.ghbt_starbucks.api.product_search;

import com.ghbt.ghbt_starbucks.api.product_search.SearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISearchCategoryRepository extends JpaRepository<SearchCategory, Long> {

}
