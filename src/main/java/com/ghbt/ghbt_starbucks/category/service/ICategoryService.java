package com.ghbt.ghbt_starbucks.category.service;

import com.ghbt.ghbt_starbucks.category.dto.RequestCategory;
import com.ghbt.ghbt_starbucks.category.dto.ResponseCategory;

import java.util.List;

public interface ICategoryService {
    ResponseCategory addCategory(RequestCategory requestCategory);
    ResponseCategory getCategory(Long id);
    List<ResponseCategory> getAllCategory();

}
