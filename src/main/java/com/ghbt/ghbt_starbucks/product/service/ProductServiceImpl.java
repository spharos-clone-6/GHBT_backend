package com.ghbt.ghbt_starbucks.product.service;

import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements IProductService{
    @Autowired
    private final IProductRepository IProductRepository;


    @Override
    public ResponseProduct addProduct(RequestProduct requestProduct){
        Product product = Product.builder()
                .name(requestProduct.getName())
                .description(requestProduct.getDescription())
                .price(requestProduct.getPrice())
                .build();
        Product resProduct = IProductRepository.save(product);

        ResponseProduct responseProduct = ResponseProduct.builder()
                .id(resProduct.getId())
                .name(resProduct.getName())
                .price(resProduct.getPrice())
                .description(resProduct.getDescription())
                .build();
        return responseProduct;
    }

    @Override
    public ResponseProduct getProduct(Long id) {
        Product product = IProductRepository.findById(id).get();
        ResponseProduct responseProduct = ResponseProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
        return responseProduct;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = IProductRepository.findAll();
        return productList;
    }

}
