package com.ghbt.ghbt_starbucks.product_and_category.service;

import com.ghbt.ghbt_starbucks.product_and_category.model.ProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.repository.IProductAndCategoryRepository;
import com.ghbt.ghbt_starbucks.product_and_category.vo.RequestProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.vo.ResponseProductAndCategory;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAndCategoryServiceImpl implements IProductAndCategoryService{

    private final IProductAndCategoryRepository IProductAndCategoryRepository;

    @Override
    public ResponseProductAndCategory addProductAndCategory(RequestProductAndCategory requestProductAndCategory) {
        ProductAndCategory productAndCategory = ProductAndCategory.builder()
                .categoryId(requestProductAndCategory.getCategoryId())
                .productId(requestProductAndCategory.getProductId())
                .build();
        ProductAndCategory resProductAndCategory = IProductAndCategoryRepository.save(productAndCategory);

        ResponseProductAndCategory responseProductAndCategory = ResponseProductAndCategory.builder()
                .id(resProductAndCategory.getId())
                .categoryId(resProductAndCategory.getCategoryId())
                .productId(resProductAndCategory.getProductId())
                .build();
        return responseProductAndCategory;
    }

    @Override
    public ResponseProductAndCategory getProductAndCategory(Long id) {
        ProductAndCategory productAndCategory = IProductAndCategoryRepository.findById(id).get();
        ResponseProductAndCategory responseProductAndCategory = ResponseProductAndCategory.builder()
                .categoryId(productAndCategory.getCategoryId())
                .productId(productAndCategory.getProductId())
                .build();
        return responseProductAndCategory;
    }
}
