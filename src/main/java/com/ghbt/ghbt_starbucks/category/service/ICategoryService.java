package com.ghbt.ghbt_starbucks.category.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.vo.RequestCategory;
import com.ghbt.ghbt_starbucks.category.vo.ResponseCategory;

import java.util.List;

public interface ICategoryService {
    ResponseCategory addCategory(RequestCategory requestCategory);
    ResponseCategory getCategory(Long id);
    List<Category> getAllCategory();
}
