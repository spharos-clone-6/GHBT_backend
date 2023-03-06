package com.ghbt.ghbt_starbucks.category.controller;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.service.ICategoryService;
import com.ghbt.ghbt_starbucks.category.vo.RequestCategory;
import com.ghbt.ghbt_starbucks.category.vo.ResponseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor

public class CategoryController {
    private final ICategoryService iCategoryService;

    @PostMapping
    public ResponseEntity addCategory(@RequestBody RequestCategory requestCategory){
        ResponseCategory responseCategory = iCategoryService.addCategory(requestCategory);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseCategory);
    }

    @GetMapping("/{id}")
    public ResponseCategory getCategory(@PathVariable Long id){
        return iCategoryService.getCategory(id);
    }
    @GetMapping
    public ResponseEntity getAllCategory(){
        List<ResponseCategory> categoryList = iCategoryService.getAllCategory();
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryList);
    }

}
