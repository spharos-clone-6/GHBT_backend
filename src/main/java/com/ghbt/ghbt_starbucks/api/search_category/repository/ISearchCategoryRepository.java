package com.ghbt.ghbt_starbucks.api.search_category.repository;

import com.ghbt.ghbt_starbucks.api.search_category.model.SearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ISearchCategoryRepository extends JpaRepository<SearchCategory, Long> {

    @Query(value = "select sc from SearchCategory sc join fetch sc.productId p where p.id = :id")
    SearchCategory getProductDetail(@Param("id") Long id);
}
