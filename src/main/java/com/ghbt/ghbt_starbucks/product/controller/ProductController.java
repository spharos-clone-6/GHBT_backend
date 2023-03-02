package com.ghbt.ghbt_starbucks.product.controller;

import com.ghbt.ghbt_starbucks.category.service.ICategoryService;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.product.service.IProductService;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import com.ghbt.ghbt_starbucks.product_and_category.service.IProductAndCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j

public class ProductController {
    private final IProductService iProductService;
    private final ICategoryService iCategoryService;
    private final IProductAndCategoryService iProductAndCategoryService;

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

    @GetMapping("/getproduct/{search}")
    public List<IProductListByCategory> findAllProductType(@PathVariable String search){
        log.info("getproduct Controller가 작동했습니다.");
        log.info("@PathVariable (\"search\") String search)가 호출 되었습니다. ");
        return iProductService.getProductForCategory(search);

    };
}
