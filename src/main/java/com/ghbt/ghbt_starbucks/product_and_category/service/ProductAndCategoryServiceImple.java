package com.ghbt.ghbt_starbucks.product_and_category.service;

import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import com.ghbt.ghbt_starbucks.product_and_category.model.ProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.repository.ProductAndCategoryRepository;
import com.ghbt.ghbt_starbucks.product_and_category.vo.RequestProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.vo.ResponseProductAndCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAndCategoryServiceImple implements IProductAndCategoryService{

    private final ProductAndCategoryRepository productAndCategoryRepository;

    @Override
    public ResponseProductAndCategory addProductAndCategory(RequestProductAndCategory requestProductAndCategory) {
        ProductAndCategory productAndCategory = ProductAndCategory.builder()
                .categoryId(requestProductAndCategory.getCategoryId())
                .productId(requestProductAndCategory.getProductId())
                .build();
        ProductAndCategory resProductAndCategory = productAndCategoryRepository.save(productAndCategory);

        ResponseProductAndCategory responseProductAndCategory = ResponseProductAndCategory.builder()
                .id(resProductAndCategory.getId())
                .categoryId(resProductAndCategory.getCategoryId())
                .productId(resProductAndCategory.getProductId())
                .build();
        return responseProductAndCategory;
    }

    @Override
    public ResponseProductAndCategory getProductAndCategory(Long id) {
        ProductAndCategory productAndCategory = productAndCategoryRepository.findById(id).get();
        ResponseProductAndCategory responseProductAndCategory = ResponseProductAndCategory.builder()
                .categoryId(productAndCategory.getCategoryId())
                .productId(productAndCategory.getProductId())
                .build();
        return responseProductAndCategory;
    }
}
