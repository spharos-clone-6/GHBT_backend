package com.ghbt.ghbt_starbucks.api.product.controller;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.service.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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

    @PostMapping // 상품 추가
    public ResponseEntity addProduct(@RequestBody List<RequestProduct> requestProductList) {
        for (RequestProduct requestProduct : requestProductList) {
            iProductService.addProduct(requestProduct);
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestProductList);
    }

    @GetMapping("/{id}") // 단건 조회
    public IProductDetail getOneProduct(@PathVariable Long id) {
        return iProductService.getOneProductId(id);
    }

    @GetMapping// 전체 상품 출력
    public Page<IProductDetail> getAllProduct(Pageable pageable) {
        return iProductService.getAllProduct(pageable);
    }


    @GetMapping("/search/{name}") // 검색어로 상품 검색
    public ResponseEntity findProduct(@PathVariable String name, Pageable pageable) {
        Page<IProductDetail> searchProduct = iProductService.getSearchProduct(name, pageable);
        return new ResponseEntity<>(searchProduct, HttpStatus.OK);
    }

    @GetMapping("/product")  // 전체 상품 조회 페이지
    public Page<Product> productPaging(final Pageable pageable) {
        return iProductService.getList(pageable);
    }

    @GetMapping("/search/type/{name}") // 검색 상품의 대분류 카테고리 갯수
    public Page<IMenubar> typeCounting(@PathVariable("name") String name, Pageable pageable) {
        return iProductService.menubarList(name, pageable);
    }

    @GetMapping("/search/c") // 카테고리별 상품 조회
    public ResponseEntity findAllProductType(@Param("filter") String filter, Pageable pageable) {
        Page<IProductDetail> searchProduct = iProductService.getCategoryName(filter, pageable);
        return ResponseEntity.status(HttpStatus.OK)
            .body(searchProduct);
    }

    @GetMapping("/search/{name}/c") // 중분류 필터링
    public Page<IProductDetail> categoryFiltering(@PathVariable String name, Pageable pageable
        , @Param("category") String[] filter) {
        return iProductService.categoryFilter(filter, name, pageable);
    }

    @GetMapping("/search/{name}/v") // 용량 필터링
    public Page<IProductDetail> volumeFiltering(@PathVariable String name, Pageable pageable
        , @Param("filter") String[] filter) {
        return iProductService.volumeFilter(filter, name, pageable);
    }

    @GetMapping("/search/{name}/s") // 시즌 필터링
    public Page<IProductDetail> seasonFiltering(@PathVariable String name, Pageable pageable
        , @Param("filter") String[] filter) {
        return iProductService.seasonFilter(filter, name, pageable);
    }

    @PutMapping("/n/{product_id}") // 상품 업데이트
    public ResponseEntity updateProduct(
        @PathVariable("product_id") Long productId,
        @RequestBody RequestProduct requestProduct) {
        iProductService.updateProduct(productId, requestProduct);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/n/{product_id}") // 상품 삭제
    public ResponseEntity deleteProduct(
        @PathVariable("product_id") Long ProductId) {
        iProductService.deleteProduct(ProductId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/n")   // 전체 상품 출력
    public List<IProductDetail> getAllProductn() {
        return iProductService.getAllProductn();
    }


    @GetMapping("/n/search/{name}") // 검색어로 상품 검색
    public ResponseEntity findProductn(@PathVariable String name) {
        List<IProductDetail> searchProduct = iProductService.getSearchProductn(name);
        return new ResponseEntity<>(searchProduct, HttpStatus.OK);
    }

    @GetMapping("/n/product")  // 전체 상품 조회 페이지
    public List<Product> productPaging() {
        return iProductService.getListn();
    }

    @GetMapping("/n/search/type/{name}") // 검색 상품의 대분류 카테고리 갯수
    public List<IMenubar> typeCounting(@PathVariable("name") String name) {
        return iProductService.menubarListn(name);
    }

    @GetMapping("/n/search/c") // 카테고리별 상품 조회
    public ResponseEntity findAllProductType(@Param("filter") String filter) {
        List<IProductDetail> searchProduct = iProductService.getCategoryNamen(filter);
        return ResponseEntity.status(HttpStatus.OK)
            .body(searchProduct);
    }

    @GetMapping("/n/search/{name}/c") // 중분류 필터링
    public List<IProductDetail> categoryFiltering(@PathVariable String name
        , @Param("category") String[] filter) {
        return iProductService.categoryFiltern(filter, name);
    }

    @GetMapping("/n/search/{name}/v") // 용량 필터링
    public List<IProductDetail> volumeFiltering(@PathVariable String name
        , @Param("filter") String[] filter) {
        return iProductService.volumeFiltern(filter, name);
    }

    @GetMapping("/n/search/{name}/s") // 시즌 필터링
    public List<IProductDetail> seasonFiltering(@PathVariable String name
        , @Param("filter") String[] filter) {
        return iProductService.seasonFiltern(filter, name);
    }

    /**
     *  상태 : 미사용
     *  기능 : 다중 필터링
     *         @GetMapping("/search/filter")
     *         public List<IProductSearch> productFiltering(
     *         @Param("categories") String[] categories,
     *         @Param("size") String[] litter,
     *         @Param("season") String[] season) {
     *         return iProductService.productFilter(categories, litter, season);}
     */
}
