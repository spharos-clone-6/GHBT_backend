package com.ghbt.ghbt_starbucks.category.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.repository.CategoryRepository;
import com.ghbt.ghbt_starbucks.category.vo.RequestCategory;
import com.ghbt.ghbt_starbucks.category.vo.ResponseCategory;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryServiceImple implements ICategoryService{

    private final CategoryRepository categoryRepository;


    @Override
    public ResponseCategory addCategory(RequestCategory requestCategory) {
        Category category = Category.builder()
                .name(requestCategory.getName())
                .type(requestCategory.getType())
                .build();
        Category resCategory = categoryRepository.save(category);

        ResponseCategory responseCategory = ResponseCategory.builder()
                .id(resCategory.getId())
                .name(resCategory.getName())
                .type(resCategory.getType())
                .build();
        return responseCategory;
    }

    @Override
    public ResponseCategory getCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        ResponseCategory responseCategory = ResponseCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
        return responseCategory;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }
}
