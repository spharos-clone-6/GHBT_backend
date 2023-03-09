package com.ghbt.ghbt_starbucks.api.category.service;

import com.ghbt.ghbt_starbucks.api.category.dto.ResponseCategory;
import com.ghbt.ghbt_starbucks.api.category.dto.RequestCategory;

import java.util.List;

public interface ICategoryService {

    void addCategory(RequestCategory requestCategory);

    ResponseCategory getCategory(Long id);

    List<ResponseCategory> getAllCategory();

}
