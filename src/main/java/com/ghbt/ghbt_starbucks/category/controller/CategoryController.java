package com.ghbt.ghbt_starbucks.category.controller;

import com.ghbt.ghbt_starbucks.category.service.ICategoryService;
import com.ghbt.ghbt_starbucks.category.dto.RequestCategory;
import com.ghbt.ghbt_starbucks.category.dto.ResponseCategory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class CategoryController {
    private final ICategoryService iCategoryService;

    @PostMapping
    public ResponseEntity addCategory(@RequestBody List<RequestCategory> requestCategoryList){
        for (RequestCategory requestCategory:requestCategoryList) {
            iCategoryService.addCategory(requestCategory);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(requestCategoryList);
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
