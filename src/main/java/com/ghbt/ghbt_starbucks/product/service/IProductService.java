package com.ghbt.ghbt_starbucks.product.service;

import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.productDto.ProductDto;
import com.ghbt.ghbt_starbucks.product.productDto.ProductListByCategory;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;

import java.util.List;

public interface IProductService {
    ResponseProduct addProduct(RequestProduct requestProduct);
    ResponseProduct getProduct(Long id);
    List<Product> getAllProduct();

    List<ProductListByCategory> getProductForCategory(String search);

}
