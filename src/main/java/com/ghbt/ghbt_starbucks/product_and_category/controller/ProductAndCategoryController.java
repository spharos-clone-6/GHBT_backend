package com.ghbt.ghbt_starbucks.product_and_category.controller;

import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import com.ghbt.ghbt_starbucks.product_and_category.service.IProductAndCategoryService;
import com.ghbt.ghbt_starbucks.product_and_category.vo.RequestProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.vo.ResponseProductAndCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productAndCategory")
@RequiredArgsConstructor
public class ProductAndCategoryController {
    private final IProductAndCategoryService iProductAndCategoryService;

    @PostMapping("/add")
    public ResponseProductAndCategory addProductAndCategory(@RequestBody RequestProductAndCategory requestProductAndCategory){
        return iProductAndCategoryService.addProductAndCategory(requestProductAndCategory);
    }

    @GetMapping("/get/{id}")
    public ResponseProductAndCategory getProductAndCategory(@PathVariable Long id){

        return iProductAndCategoryService.getProductAndCategory(id);
    }

}
