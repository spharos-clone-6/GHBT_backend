package com.ghbt.ghbt_starbucks.api.product.repository;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.security.core.parameters.P;

public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p AS thumbnail_url from Product p left join ProductAndCategory pac on p.id = pac.productId.id left join pac.categoryId c where c.name = :search")
    List<IProductListByCategory> findAllProductType(@Param("search") String search);

    //상품만 검색되는 쿼리
    @Query(value = "select p from Product p where p.name like %:search%")
    List<IProductSearch> findProduct(@Param("search") String search);

    //대분류 갯수 구하는 쿼리
    @Query(value = "select c.name as typeName , count(c.name) as typeCount from Category c left join ProductAndCategory pac on c.id = pac.categoryId.id left join pac.productId p where c.type ='대' and p.name like %:name% group by c.name ")
    List<IMenubar> findByMenubarList(@Param("name") String name);

    //특정 단어가 포함되는 검색어 검색
    @Query(value = "select p,c from Product p left join ProductAndCategory pac on p.id = pac.productId.id left join pac.categoryId c where p.name like %:search%")
    List<List<Product>> findByNameContains(@Param("search") String search);

    @Query(value = "select p from Product p left join ProductAndCategory pac on p.id = pac.productId.id left join pac.categoryId c where c.name in :categories or c.name in :season or c.name in :litter")
    List<IProductSearch> findCategoryList(@Param("categories") String[] categories,
        @Param("litter") String[] litter,
        @Param("season") String[] season);

//    Page<Product> findByNameContains(String keyWord, Pageable pageable);

//    List<Product> findByNameContains(String keyWord);
}
