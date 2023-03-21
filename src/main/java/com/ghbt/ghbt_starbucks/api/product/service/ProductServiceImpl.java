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


    @Override // 카테고리 테이블 바꾸기
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

    @Override
    public IProductDetail getOneProduct(Long id) {
        IProductDetail productDetail = iProductRepository.findOneProduct(id);

        return productDetail;
    }


    @Override
    public List<ResponseProduct> getAllProduct() {
        List<Product> productList = iProductRepository.findAll();
        if (productList.isEmpty()) {
            throw new ServiceException("상품이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return ResponseProduct.mapper(productList);
    }

    @Override
    public Page<IProductListByCategory> getCategoryName(String search, Pageable pageable) {
        Page<IProductListByCategory> productList = iProductRepository.findCategoryName(search, pageable);
        if (productList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return productList;
    }
//    @Override
//    public List<List<ProductAndCategory>> searchingCategoryList(String keyWord) {
//
//        List<List<ProductAndCategory>> resultList = new ArrayList<>();
//        List<Product> productList = iProductRepository.findByNameContains(keyWord);
//        if (productList.isEmpty()) {
//            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
//        }
//
//        for (Product product : productList) {
//            List<ProductAndCategory> productAndCategoryList = iProductAndCategoryRepository.findByProductId(product);
//            resultList.add(productAndCategoryList);
//        }
//        return resultList;
//    }


    @Override
    public Page<IProductSearch> getSearchProduct(String search, Pageable pageable) {
        Page<IProductSearch> productList = iProductRepository.findProduct(search, pageable);
        if (productList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return productList;
    }

    @Override
    public Page<Product> getList(Pageable pageable) {
        return iProductRepository.findAll(pageable);


    }

    @Override
    public Product updateProduct(Long ProductId, RequestProduct requestProduct) {
        Product product = iProductRepository.findById(requestProduct.getId())
            .orElseThrow(() -> new ServiceException("찾으려는 ID의 상품이 없습니다", HttpStatus.NO_CONTENT));
        product.updateProduct(requestProduct.getName(), requestProduct.getPrice(), requestProduct.getDescription(),
            requestProduct.getStock(),
            requestProduct.getLikeCount(), requestProduct.getThumbnailUrl(), requestProduct.getIsBest());
        iProductRepository.save(product);
        return product;
    }

    @Override
    public Page<IMenubar> menubarList(String name, Pageable pageable) {
        Page<IMenubar> menubar = iProductRepository.findByMenubarList(name, pageable);
        return menubar;
    }

    @Override
    public Page<IProductSearch> categoryFilter(String[] filter, String search, Pageable pageable) {
        Page<IProductSearch> categoryList;
        if (search.equals("-")) {
            categoryList = iProductRepository.findCategoryFilter(filter, pageable);
        } else {
            categoryList = iProductRepository.findSearchCategoryFilter(filter, pageable, search);
        }
        if (categoryList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return categoryList;
    }

    @Override
    public Page<IProductSearch> volumeFilter(String[] filter, String search, Pageable pageable) {
        Page<IProductSearch> volumeList;
        if (search.equals("-")) {
            volumeList = iProductRepository.findVolumeFilter(filter, pageable);
        } else {
            volumeList = iProductRepository.findSearchVolumeFilter(filter, pageable, search);
        }
        if (volumeList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return volumeList;
    }

    @Override
    public Page<IProductSearch> seasonFilter(String[] filter, String search, Pageable pageable) {
        Page<IProductSearch> seasonList;
        if (search.equals("-")) {
            seasonList = iProductRepository.findSeasonFilter(filter, pageable);
        } else {
            seasonList = iProductRepository.findSearchSeasonFilter(filter, pageable, search);
        }
        if (seasonList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return seasonList;
    }

//    @Override // and 조건
//    public List<IProductSearch> productFilter(String[] categories, String[] season, String[] litter) {
//        List<IProductSearch> products = iProductRepository.findCategoryList(categories, season, litter);
//
//        return products;
//    }

    @Override
    public void deleteProduct(Long ProductId) {
        iProductRepository.deleteById(ProductId);
    }
}
