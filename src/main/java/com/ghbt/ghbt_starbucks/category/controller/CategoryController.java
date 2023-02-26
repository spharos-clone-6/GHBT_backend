package com.ghbt.ghbt_starbucks.category.controller;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.service.ICategoryService;
import com.ghbt.ghbt_starbucks.category.vo.RequestCategory;
import com.ghbt.ghbt_starbucks.category.vo.ResponseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor

public class CategoryController {
    private final ICategoryService iCategoryService;

    @PostMapping("/add")
    public ResponseCategory addCategory(@RequestBody RequestCategory requestCategory){
        return iCategoryService.addCategory(requestCategory);
    }

    @GetMapping("/get/{id}")
    public ResponseCategory getCategory(@PathVariable Long id){
        return iCategoryService.getCategory(id);
    }
    @GetMapping("/get")
    public List<Category> getAllCategory(){
        return iCategoryService.getAllCategory();
    }
}
