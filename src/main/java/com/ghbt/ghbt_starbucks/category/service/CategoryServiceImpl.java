package com.ghbt.ghbt_starbucks.category.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.repository.ICategoryRepository;
import com.ghbt.ghbt_starbucks.category.vo.RequestCategory;
import com.ghbt.ghbt_starbucks.category.vo.ResponseCategory;
import com.ghbt.ghbt_starbucks.error.ServiceException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor

public class CategoryServiceImpl implements ICategoryService{

    private final ICategoryRepository iCategoryRepository;


    @Override
    public ResponseCategory addCategory(RequestCategory requestCategory) {
        Category category = Category.builder()
                .name(requestCategory.getName())
                .type(requestCategory.getType())
                .build();
        Category resCategory = iCategoryRepository.save(category);

        ResponseCategory responseCategory = ResponseCategory.builder()
                .id(resCategory.getId())
                .name(resCategory.getName())
                .type(resCategory.getType())
                .build();
        return responseCategory;
    }

    @Override
    public ResponseCategory getCategory(Long id) {
        Category category = iCategoryRepository.findById(id).orElseThrow(()->new ServiceException("존재하지 않는 ID 입니다.", HttpStatus.NO_CONTENT));
        ResponseCategory responseCategory = ResponseCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
        return responseCategory;
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
