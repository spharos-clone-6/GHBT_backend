package com.ghbt.ghbt_starbucks.api.product.repository;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Long> {

    //상품만 검색되는 쿼리
    @Query(value = "select p from Product p where p.name like %:search%")
    Page<IProductSearch> findProduct(@Param("search") String search, Pageable pageable);

    //대분류 갯수 구하는 쿼리
    @Query(value = "select c.name as typeName , count(c.name) as typeCount from Category c left join ProductAndCategory pac on c.id = pac.categoryId.id left join pac.productId p where c.type ='대' and p.name like %:name% group by c.name ")
    Page<IMenubar> findByMenubarList(@Param("name") String name, Pageable pageable);

//    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.subType in :categories and s.season in :season and s.volume in :litter")
//    List<IProductSearch> findCategoryList(@Param("categories") String[] categories,
//        @Param("litter") String[] litter,
//        @Param("season") String[] season);
}
