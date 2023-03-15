package com.ghbt.ghbt_starbucks.api.product.repository;

import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.api.product_and_category.model.ProductAndCategory;
import javax.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p AS thumbnail_url from Product p left join ProductAndCategory pac on p.id = pac.productId.id left join pac.categoryId c where c.name = :search")
    List<Product> findAllProductType(@Param("search") String search);

    @Query(value = "SELECT p FROM Product p where p.name LIKE %:search%")
    List<IProductSearch> findProduct(@Param("search") String search);

//    Page<Product> findByNameContains(String keyWord, Pageable pageable);

//    List<Product> findByNameContains(String keyWord);


    @Query(value = "SELECT p,c FROM Product p left join ProductAndCategory pac on p.id = pac.productId.id left join pac.categoryId c where p.name like %:search%")
    Page<List<Product>> findByNameContains(@Param("search") String search, Pageable pageable);
}
