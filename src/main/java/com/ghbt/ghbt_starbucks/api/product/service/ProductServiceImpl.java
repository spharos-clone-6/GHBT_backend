package com.ghbt.ghbt_starbucks.api.product.service;

import com.ghbt.ghbt_starbucks.api.category.model.Category;
import com.ghbt.ghbt_starbucks.api.category.repository.ICategoryRepository;
import com.ghbt.ghbt_starbucks.api.product.Projection.IMenubar;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.dto.ResponseProduct;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.product_and_category.model.ProductAndCategory;
import com.ghbt.ghbt_starbucks.api.search_category.model.SearchCategory;
import com.ghbt.ghbt_starbucks.api.search_category.repository.ISearchCategoryRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.api.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.api.product_and_category.repository.IProductAndCategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final IProductRepository iProductRepository;
    private final IProductAndCategoryRepository iProductAndCategoryRepository;
    private final ICategoryRepository iCategoryRepository;
    private final ISearchCategoryRepository iSearchCategoryRepository;


    @Override // 상품 추가
    public void addProduct(RequestProduct requestProduct) {
        Product product = Product.builder()
            .name(requestProduct.getName()).description(requestProduct.getDescription())
            .price(requestProduct.getPrice()).thumbnailUrl(requestProduct.getThumbnailUrl())
            .stock(requestProduct.getStock())
            .build();

        Product savedProduct = iProductRepository.save(product);
        for (String name : requestProduct.getCategoryList()) {
            if (!name.isEmpty()) {
                Category findCategory = iCategoryRepository.findByName(name);
                ProductAndCategory productAndCategory = ProductAndCategory.builder().productId(savedProduct)
                    .categoryId(findCategory).build();
                iProductAndCategoryRepository.save(productAndCategory);
            }
        }
        SearchCategory searchCategory = SearchCategory.builder()
            .productId(savedProduct)
            .bigType(requestProduct.getCategoryList().get(0))
            .subType(requestProduct.getCategoryList().get(1))
            .season(requestProduct.getCategoryList().get(2))
            .volume(requestProduct.getCategoryList().get(3))
            .build();
        iSearchCategoryRepository.save(searchCategory);
    }

    @Override // 상품 단건 조회
    public IProductDetail getOneProductId(Long id) {
        IProductDetail productDetail = iSearchCategoryRepository.getOneProductId(id);

        return productDetail;
    }


    @Override // no 페이징 전체 상품
    public Page<IProductDetail> getAllProduct(Pageable pageable) {
        Page<IProductDetail> productList = iSearchCategoryRepository.getAllProduct(pageable);
        if (productList.isEmpty()) {
            throw new ServiceException("상품이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return productList;
    }

    @Override // 상품명 검색
    public Page<IProductDetail> getSearchProduct(String search, Pageable pageable) {
        Page<IProductDetail> productList = iSearchCategoryRepository.getProductName(search, pageable);
        if (productList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return productList;
    }

    @Override // 상품 전체 페이징
    public Page<Product> getList(Pageable pageable) {
        return iProductRepository.findAll(pageable);


    }

    @Override // 메뉴바 출력
    public Page<IMenubar> menubarList(String name, Pageable pageable) {
        Page<IMenubar> menubar = iProductRepository.findByMenubarList(name, pageable);
        return menubar;
    }

    @Override // 카테고리 필터링
    public Page<IProductDetail> getCategoryName(String filter, Pageable pageable) {
        Page<IProductDetail> productList = iSearchCategoryRepository.findCategoryName(filter, pageable);
        if (productList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return productList;
    }


    @Override // 중분류 필터
    public Page<IProductDetail> categoryFilter(String[] filter, String search, Pageable pageable) {
        Page<IProductDetail> categoryList;
        if (search.equals("-")) {
            categoryList = iSearchCategoryRepository.findCategoryFilter(filter, pageable);
        } else {
            categoryList = iSearchCategoryRepository.findSearchCategoryFilter(filter, pageable, search);
        }
        if (categoryList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return categoryList;
    }

    @Override // 용량 필터
    public Page<IProductDetail> volumeFilter(String[] filter, String search, Pageable pageable) {
        Page<IProductDetail> volumeList;
        if (search.equals("-")) {
            volumeList = iSearchCategoryRepository.findVolumeFilter(filter, pageable);
        } else {
            volumeList = iSearchCategoryRepository.findSearchVolumeFilter(filter, pageable, search);
        }
        if (volumeList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return volumeList;
    }

    @Override // 시즌 필터
    public Page<IProductDetail> seasonFilter(String[] filter, String search, Pageable pageable) {
        Page<IProductDetail> seasonList;
        if (search.equals("-")) {
            seasonList = iSearchCategoryRepository.findSeasonFilter(filter, pageable);
        } else {
            seasonList = iSearchCategoryRepository.findSearchSeasonFilter(filter, pageable, search);
        }
        if (seasonList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return seasonList;
    }

//    @Override // 필터 and 조건
//    public List<IProductSearch> productFilter(String[] categories, String[] season, String[] litter) {
//        List<IProductSearch> products = iProductRepository.findCategoryList(categories, season, litter);
//
//        return products;
//    }

    @Override // 상품 업데이터
    public Product updateProduct(Long ProductId, RequestProduct requestProduct) {
        Product product = iProductRepository.findById(requestProduct.getId())
            .orElseThrow(() -> new ServiceException("찾으려는 ID의 상품이 없습니다", HttpStatus.NO_CONTENT));
        product.updateProduct(requestProduct.getName(), requestProduct.getPrice(), requestProduct.getDescription(),
            requestProduct.getStock(),
            requestProduct.getLikeCount(), requestProduct.getThumbnailUrl(), requestProduct.getIsBest());
        iProductRepository.save(product);
        return product;
    }

    @Override // 상품삭제
    public void deleteProduct(Long ProductId) {
        iProductRepository.deleteById(ProductId);
    }
}
