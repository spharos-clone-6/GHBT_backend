package com.ghbt.ghbt_starbucks.product.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.repository.ICategoryRepository;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import com.ghbt.ghbt_starbucks.product_and_category.model.ProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.repository.IProductAndCategoryRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor

public class ProductServiceImpl implements IProductService{
    @Autowired
    private final IProductRepository iProductRepository;
    private final IProductAndCategoryRepository iProductAndCategoryRepository;
    private final ICategoryRepository iCategoryRepository;


    @Override
    public ResponseProduct addProduct(RequestProduct requestProduct){
        Product product = Product.builder()
                .name(requestProduct.getName())
                .description(requestProduct.getDescription())
                .price(requestProduct.getPrice())
                .build();
        Product resProduct = iProductRepository.save(product);

        for (Category cate :requestProduct.getCategoryList()) {
            Category category= iCategoryRepository.findByName(cate.getName());
            ProductAndCategory productAndCategory = ProductAndCategory.builder()
                    .id(resProduct.getId())
                    .categoryId(category)
                    .build();
            ProductAndCategory resProductAndCategory = iProductAndCategoryRepository.save(productAndCategory);
        }



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
        Product product = iProductRepository.findById(id).get();
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
        List<Product> productList = iProductRepository.findAll();
        return productList;
    }

}
