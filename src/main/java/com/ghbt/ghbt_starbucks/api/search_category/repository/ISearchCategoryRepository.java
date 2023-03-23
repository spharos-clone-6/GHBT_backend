package com.ghbt.ghbt_starbucks.api.search_category.repository;

import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.search_category.model.SearchCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ISearchCategoryRepository extends JpaRepository<SearchCategory, Long> {

    //단건 조회
    @Query(value = "select p.name as name,p.price as price, p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from SearchCategory s left join s.productId p where p.id = :id")
    IProductDetail getOneProductId(@Param("id") Long id);

    //전체 조회
    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from SearchCategory s left join s.productId p")
    Page<IProductDetail> getAllProduct(Pageable pageable);

    //상품 이름 검색
    @Query(value = "select p.name as name,p.price as price,p.stock as stock ,p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from SearchCategory s left join s.productId p where p.name like %:name%")
    Page<IProductDetail> getProductName(@Param("name") String name, Pageable pageable);

    // 카테고리 이름으로 검색
    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from Product p left join SearchCategory s on s.productId.id = p.id where s.bigType in :filter or s.subType in :filter or s.volume in :filter or s.season in :filter")
    Page<IProductDetail> findCategoryName(@Param("filter") String filter, Pageable pageable);

    // 중분류, 사이즈, 시즌 필터 (검색어 포함, 미포함)
    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from Product p left join SearchCategory s on s.productId.id = p.id where s.subType in :filter")
    Page<IProductDetail> findCategoryFilter(@Param("filter") String[] filter, Pageable pageable);

    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from Product p left join SearchCategory s on s.productId.id = p.id where s.subType in :filter and p.name like %:search%")
    Page<IProductDetail> findSearchCategoryFilter(@Param("filter") String[] filter, Pageable pageable,
        @Param("search") String search);

    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from Product p left join SearchCategory s on s.productId.id = p.id where s.volume in :filter")
    Page<IProductDetail> findVolumeFilter(@Param("filter") String[] filter, Pageable pageable);

    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from Product p left join SearchCategory s on s.productId.id = p.id where s.volume in :filter and p.name like %:search%")
    Page<IProductDetail> findSearchVolumeFilter(@Param("filter") String[] filter, Pageable pageable,
        @Param("search") String search);

    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from Product p left join SearchCategory s on s.productId.id = p.id where s.season in :filter")
    Page<IProductDetail> findSeasonFilter(@Param("filter") String[] filter, Pageable pageable);

    @Query(value = "select p.name as name,p.price as price,p.stock as stock, p.description as description, p.thumbnailUrl as thumbnailUrl,p.isNew as isNew,p.likeCount as likeCount,p.isBest as isBest, p.id as productId, s.bigType as bigType,s.subType as subType, s.season as season,s.volume as volume from Product p left join SearchCategory s on s.productId.id = p.id where s.season in :filter and p.name like %:search%")
    Page<IProductDetail> findSearchSeasonFilter(@Param("filter") String[] filter, Pageable pageable,
        @Param("search") String search);

}