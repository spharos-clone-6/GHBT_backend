package com.ghbt.ghbt_starbucks.product.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;

import java.util.List;

public interface IProductService {
    ResponseProduct addProduct(RequestProduct requestProduct);
    ResponseProduct getProduct(Long id);
    List<Product> getAllProduct();

    Category getProductForCategory(String name);

}
