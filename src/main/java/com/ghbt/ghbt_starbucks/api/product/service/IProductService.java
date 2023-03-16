package com.ghbt.ghbt_starbucks.api.product.service;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
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

    ResponseProduct getProduct(Long id);

    List<ResponseProduct> getAllProduct();

    List<IProductListByCategory> getProductForCategory(String search);

    List<IProductSearch> getSearchProduct(String search);

    Page<Product> getList(Pageable pageable);

    Product updateProduct(Long ProductId, RequestProduct requestProduct);

    List<List<Product>> searchingCategoryList(String name);

    List<IMenubar> menubarList(String name);

    void deleteProduct(Long ProductId);
}

