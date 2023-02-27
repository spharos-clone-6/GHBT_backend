package com.ghbt.ghbt_starbucks.product.controller;

import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.service.IProductService;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor

public class ProductController {
    private final IProductService iProductService;

    @PostMapping("/add")
    public ResponseProduct addProduct(@RequestBody RequestProduct requestProduct){
        return iProductService.addProduct(requestProduct);
    }

    @GetMapping("/get/{id}")
    public ResponseProduct getProduct(@PathVariable Long id){

        return iProductService.getProduct(id);
    }
    @GetMapping("/get")
    public List<Product> getAllProduct(){
        return iProductService.getAllProduct();
    }
}
