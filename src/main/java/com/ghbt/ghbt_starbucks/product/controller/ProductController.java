package com.ghbt.ghbt_starbucks.product.controller;

import com.ghbt.ghbt_starbucks.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.product.service.IProductService;
import com.ghbt.ghbt_starbucks.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.product.dto.ResponseProduct;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")

public class ProductController {
    private final IProductService iProductService;
    private final IProductRepository iProductRepository;

    @PostMapping
    public ResponseEntity addProduct(@RequestBody List<RequestProduct> requestProductList) {
        for (RequestProduct requestProduct : requestProductList) {
            iProductService.addProduct(requestProduct);
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestProductList);
    }

    @GetMapping("/{productId}") // 상품 추가
    public ResponseProduct getProduct(@PathVariable Long productId) {

        return iProductService.getProduct(productId);
    }

    //    @GetMapping // 전체 상품 출력
//    public List<Product> getAllProduct(){
//        return iProductService.getAllProduct();
//    }
    @GetMapping("/search-category/{search}") // 카테고리별 상품 조회
    public ResponseEntity findAllProductType(@PathVariable String search) {
        List<IProductListByCategory> searchProduct = iProductService.getProductForCategory(search);
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchProduct);
    }

    @GetMapping("/search/{search}") // 검색어로 상품 검색
    public ResponseEntity findProduct(@PathVariable String search) {
        List<IProductSearch> searchProduct = iProductService.getSearchProduct(search);
        return ResponseEntity.status(HttpStatus.OK)
                .body(searchProduct);
    }

    @GetMapping // 전체 상품 조회 페이지
    public Page<Product> productPaging(final Pageable pageable) {
        return iProductService.getList(pageable);
    }

    @GetMapping("/product/{keyWord}") // 검색 상품 조회 페이지
    public Page<Product> getAllProductWithPageByQueryMethod(@PathVariable String keyWord)
//                                                          @RequestParam("page") Integer page)
    {
        PageRequest pageRequest = PageRequest.of(0, 20);
        return iProductRepository.findByNameContains(keyWord, pageRequest);
    }


    @PutMapping("/{product_id}") // 상품 업데이트
    public ResponseEntity updateProduct(
            @PathVariable("product_id") Long productId,
            @RequestBody RequestProduct requestProduct) {
        iProductService.updateProduct(productId, requestProduct);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{product_id}") // 상품 삭제
    public ResponseEntity deleteProduct(
            @PathVariable("product_id") Long ProductId) {
        iProductService.deleteProduct(ProductId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
