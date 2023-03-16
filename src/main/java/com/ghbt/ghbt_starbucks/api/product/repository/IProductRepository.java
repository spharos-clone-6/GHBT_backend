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

public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p.id as id, p.name as name, p.price as price, p.thumbnail_url as thumbnail_url from product_and_category pac left join category c on pac.category_id =c.id left join product p on pac.product_id  = p.id where c.name = :search", nativeQuery = true)
    List<IProductListByCategory> findAllProductType(@Param("search") String search);

    @Query(value = "select p from Product p where p.name like %:search%")
    List<IProductSearch> findProduct(@Param("search") String search);

    @Query(value = "select c.name as typeName , count(c.name) as typeCount from Category c left join ProductAndCategory pac on c.id = pac.categoryId.id left join pac.productId p where c.type ='대' GROUP BY c.name")
    List<IMenubar> findByMenubarList(List<Product> products);


    @Query(value = "select p,c from Product p left join ProductAndCategory pac on p.id = pac.productId.id left join pac.categoryId c where p.name like %:search%")
    List<List<Product>> findByNameContains(@Param("search") String search);

//    Page<Product> findByNameContains(String keyWord, Pageable pageable);

//    List<Product> findByNameContains(String keyWord);
}
