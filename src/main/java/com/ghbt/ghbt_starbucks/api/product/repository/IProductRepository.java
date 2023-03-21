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

    // 카테고리 이름으로 검색
    @Query(value = "select p from Product p left join ProductAndCategory pac on p.id = pac.productId.id left join pac.categoryId c on c.id = pac.categoryId.id where c.name = :search")
    Page<IProductListByCategory> findCategoryName(@Param("search") String search, Pageable pageable);

    @Query(value = "select  p.name as name,p.price as price, p.description as description, p.id as productId, s.bigType as bigType ,s.subType as subType, s.season as season,s.volume as volume from SearchCategory s left join Product p on s.productId.id = p.id where p.id = :search")
    IProductDetail findOneProduct(@Param("search") Long search);

    //상품만 검색되는 쿼리
    @Query(value = "select p from Product p where p.name like %:search%")
    Page<IProductSearch> findProduct(@Param("search") String search, Pageable pageable);

    //대분류 갯수 구하는 쿼리
    @Query(value = "select c.name as typeName , count(c.name) as typeCount from Category c left join ProductAndCategory pac on c.id = pac.categoryId.id left join pac.productId p where c.type ='대' and p.name like %:name% group by c.name ")
    Page<IMenubar> findByMenubarList(@Param("name") String name, Pageable pageable);

    // 중분류, 사이즈, 시즌 필터 (검색어 포함, 미포함)
    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.subType in :filter")
    Page<IProductSearch> findCategoryFilter(@Param("filter") String[] filter, Pageable pageable);

    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.subType in :filter and p.name like %:search%")
    Page<IProductSearch> findSearchCategoryFilter(@Param("filter") String[] filter, Pageable pageable,
        @Param("search") String search);

    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.volume in :filter")
    Page<IProductSearch> findVolumeFilter(@Param("filter") String[] filter, Pageable pageable);

    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.volume in :filter and p.name like %:search%")
    Page<IProductSearch> findSearchVolumeFilter(@Param("filter") String[] filter, Pageable pageable,
        @Param("search") String search);

    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.season in :filter")
    Page<IProductSearch> findSeasonFilter(@Param("filter") String[] filter, Pageable pageable);

    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.season in :filter and p.name like %:search%")
    Page<IProductSearch> findSearchSeasonFilter(@Param("filter") String[] filter, Pageable pageable,
        @Param("search") String search);

//    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.season in :season and p.name like %:search%")
//    List<IProductSearch> findSeasonFilter(@Param("season") String[] season, @Param("search") String search);
//
//    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.volume in :volume and p.name like %:search%")
//    List<IProductSearch> findVolumeFilter(@Param("volume") String[] volume, @Param("search") String search);

//    @Query(value = "select p from Product p left join SearchCategory s on s.productId.id = p.id where s.subType in :categories and s.season in :season and s.volume in :litter")
//    List<IProductSearch> findCategoryList(@Param("categories") String[] categories,
//        @Param("litter") String[] litter,
//        @Param("season") String[] season);

//    Page<Product> findByNameContains(String keyWord, Pageable pageable);

//    List<Product> findByNameContains(String keyWord);
}
