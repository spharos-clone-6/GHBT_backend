package com.ghbt.ghbt_starbucks.api.category.service;

import com.ghbt.ghbt_starbucks.api.category.dto.ResponseCategory;
import com.ghbt.ghbt_starbucks.api.category.model.Category;
import com.ghbt.ghbt_starbucks.api.category.repository.ICategoryRepository;
import com.ghbt.ghbt_starbucks.api.category.dto.RequestCategory;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor

public class CategoryServiceImpl implements ICategoryService{

    private final ICategoryRepository iCategoryRepository;


    @Override
    public void addCategory(RequestCategory requestCategory) {
        Category category = Category.builder()
                .name(requestCategory.getName())
                .type(requestCategory.getType())
                .build();
        Category saveCategory = iCategoryRepository.save(category);

        ResponseCategory.builder()
                .id(saveCategory.getId())
                .name(saveCategory.getName())
                .type(saveCategory.getType())
                .build();
    }

    @Override
    public ResponseCategory getCategory(Long id) {
        Category category = iCategoryRepository.findById(id).orElseThrow(()->new ServiceException("존재하지 않는 ID 입니다.", HttpStatus.NO_CONTENT));

        return ResponseCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
    }


    @Override
    public List<ResponseCategory> getAllCategory() {
        List<Category> categoryList = iCategoryRepository.findAll();
        if(categoryList.isEmpty()){
            throw new ServiceException("검색 결과가 없습니다.",HttpStatus.NO_CONTENT);
        }
        return ResponseCategory.mapper(categoryList);
    }
}
