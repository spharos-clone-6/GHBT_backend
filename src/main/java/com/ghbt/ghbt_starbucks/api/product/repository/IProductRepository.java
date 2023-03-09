package com.ghbt.ghbt_starbucks.api.product.repository;

import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p.id AS id, p.name AS name, p.price AS price, p.thumbnail_url AS thumbnail_url from product_and_category pac left join category c on pac.category_id =c.id left join product p on pac.product_id  = p.id where c.name = :search",nativeQuery = true)
    List<IProductListByCategory> findAllProductType (@Param("search") String search);

    @Query(value = "SELECT id AS id, name AS name, price AS price, description AS description, stock AS stock, is_best AS is_best,like_count AS like_count,thumbnail_url AS thumbnail_url FROM product where name LIKE %:search%", nativeQuery = true)
    List<IProductSearch> findProduct (@Param("search")  String search);

    Page<Product> findByNameContains(String keyWord, Pageable pageable);
}
