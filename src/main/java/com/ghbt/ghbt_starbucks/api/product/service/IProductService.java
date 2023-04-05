package com.ghbt.ghbt_starbucks.api.product.service;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    void addProduct(RequestProduct requestProduct);

    IProductDetail getOneProductId(Long id);

    Page<IProductDetail> getAllProduct(Pageable pageable);

    Page<IProductDetail> getCategoryName(String filter, Pageable pageable);

    Page<IProductDetail> getSearchProduct(String search, Pageable pageable);

    Page<Product> getList(Pageable pageable);

    Product updateProduct(Long ProductId, RequestProduct requestProduct);

    Page<IMenubar> menubarList(String name, Pageable pageable);

    Page<IProductDetail> categoryFilter(String[] filter, String search, Pageable pageable);

    Page<IProductDetail> seasonFilter(String[] filter, String search, Pageable pageable);

    Page<IProductDetail> volumeFilter(String[] filter, String search, Pageable pageable);

    void deleteProduct(Long ProductId);

    /**
     *============================================= << 페이징 유 무 >> ======================================================
     */
    List<IProductDetail> getAllProductn();

    List<IProductDetail> getCategoryNamen(String filter);

    List<IProductDetail> getSearchProductn(String search);

    List<Product> getListn();

    List<IMenubar> menubarListn(String name);

    List<IProductDetail> categoryFiltern(String[] filter, String search);

    List<IProductDetail> seasonFiltern(String[] filter, String search);

    List<IProductDetail> volumeFiltern(String[] filter, String search);
}

/**
 * List<IProductSearch> productFilter(String[] categories, String[] season, String[] litter); and 조건
 */
