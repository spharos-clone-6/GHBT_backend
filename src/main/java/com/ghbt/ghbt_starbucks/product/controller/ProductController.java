package com.ghbt.ghbt_starbucks.product.controller;

import com.ghbt.ghbt_starbucks.category.service.ICategoryService;
import com.ghbt.ghbt_starbucks.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.product.service.IProductService;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j

public class ProductController {
    private final IProductService iProductService;
    private final ICategoryService iCategoryService;
    @PostMapping
    public ResponseProduct addProduct(@RequestBody RequestProduct requestProduct){
        return iProductService.addProduct(requestProduct);
    }

    @GetMapping("/{productId}") // 상품 추가
    public ResponseProduct getProduct(@PathVariable Long productId){

        return iProductService.getProduct(productId);
    }
//    @GetMapping // 전체 상품 출력
//    public List<Product> getAllProduct(){
//        return iProductService.getAllProduct();
//    }

    @GetMapping("/category-search/{search}") // 카테고리별 상품 조회
    public ResponseEntity findAllProductType(@PathVariable String search){
        List<IProductListByCategory> searchProduct = iProductService.getProductForCategory(search);
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchProduct);
    }
    @GetMapping("/search/{search}") // 검색어로 상품 검색
    public ResponseEntity findProduct(@PathVariable String search){
        List<IProductSearch> searchProduct = iProductService.getSearchProduct(search);
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchProduct);
    }

    @GetMapping
    public Page<Product> productPaging(final Pageable pageable){
        return iProductService.getList(pageable);
    }

}
