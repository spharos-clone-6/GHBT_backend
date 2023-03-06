package com.ghbt.ghbt_starbucks.product.service;

import com.ghbt.ghbt_starbucks.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ResponseProduct addProduct(RequestProduct requestProduct);
    ResponseProduct getProduct(Long id);
    List<ResponseProduct> getAllProduct();

    List<IProductListByCategory> getProductForCategory(String search);
    List<IProductSearch> getSearchProduct(String search);

    Page<Product> getList(Pageable pageable);

    }
