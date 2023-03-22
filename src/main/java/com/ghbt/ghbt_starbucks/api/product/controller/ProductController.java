package com.ghbt.ghbt_starbucks.api.product.controller;

import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.api.product.dto.ResponseProduct;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.api.product.service.IProductService;
import com.ghbt.ghbt_starbucks.api.search_category.model.SearchCategory;
import com.ghbt.ghbt_starbucks.api.search_category.repository.ISearchCategoryRepository;
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

    //    @GetMapping("/search/filter") // 카테고리 필터링
//    public List<IProductSearch> productFiltering(
//        @Param("categories") String[] categories,
//        @Param("size") String[] litter,
//        @Param("season") String[] season) {
//        return iProductService.productFilter(categories, litter, season);}

    //    @GetMapping("/product/{keyWord}") // 검색 상품 조회 페이지
//    public Page<Product> getAllProductWithPageByQueryMethod(@PathVariable String keyWord) {
//        PageRequest pageRequest = PageRequest.of(0, 20);
//        return iProductRepository.findByNameContains(keyWord, pageRequest);
//    }
//

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
