package com.ghbt.ghbt_starbucks.api.product.service;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.api.product.dto.ResponseProduct;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    void addProduct(RequestProduct requestProduct);

    IProductDetail getOneProduct(Long id);

    List<ResponseProduct> getAllProduct();

    Page<IProductListByCategory> getCategoryName(String search, Pageable pageable);

    Page<IProductSearch> getSearchProduct(String search, Pageable pageable);

    Page<Product> getList(Pageable pageable);

    Product updateProduct(Long ProductId, RequestProduct requestProduct);

    Page<IMenubar> menubarList(String name, Pageable pageable);

    //    List<IProductSearch> productFilter(String[] categories, String[] season, String[] litter); and 조건
    Page<IProductSearch> categoryFilter(String[] filter, String search, Pageable pageable);

    Page<IProductSearch> seasonFilter(String[] filter, String search, Pageable pageable);

    Page<IProductSearch> volumeFilter(String[] filter, String search, Pageable pageable);

    void deleteProduct(Long ProductId);
}

