package com.ghbt.ghbt_starbucks.product.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.repository.ICategoryRepository;
import com.ghbt.ghbt_starbucks.error.ServiceException;
import com.ghbt.ghbt_starbucks.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.product.dto.RequestProduct;
import com.ghbt.ghbt_starbucks.product.dto.ResponseProduct;
import com.ghbt.ghbt_starbucks.product_and_category.model.ProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.repository.IProductAndCategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{
    @Autowired
    private final IProductRepository iProductRepository;
    private final IProductAndCategoryRepository iProductAndCategoryRepository;
    private final ICategoryRepository iCategoryRepository;


    @Override
    public ResponseProduct addProduct(RequestProduct requestProduct){
        Product product = Product.builder()
                .name(requestProduct.getName())
                .description(requestProduct.getDescription())
                .price(requestProduct.getPrice())
                .thumbnailUrl(requestProduct.getThumbnailUrl())
                .stock(requestProduct.getStock())
                .build();
        Product savedProduct = iProductRepository.save(product);

        for (String name :requestProduct.getCategoryList()) {
            Category findCategory = iCategoryRepository.findByName(name);
            ProductAndCategory productAndCategory = ProductAndCategory.builder()
                    .product(savedProduct)
                    .category(findCategory)
                    .build();
            ProductAndCategory resProductAndCategory = iProductAndCategoryRepository.save(productAndCategory);
        }

        ResponseProduct responseProduct = ResponseProduct.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .description(savedProduct.getDescription())
                .thumbnailUrl(savedProduct.getThumbnailUrl())
                .stock(savedProduct.getStock())
                .likeCount(savedProduct.getLikeCount())
                .isBest(savedProduct.getIsBest())
                .build();
        return responseProduct;
    }
    @Override
    public ResponseProduct getProduct(Long id) {
        Product product =iProductRepository.findById(id).orElseThrow(()-> new ServiceException("찾으려는 ID의 상품이 없습니다", HttpStatus.NO_CONTENT));
        ResponseProduct responseProduct = ResponseProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .stock(product.getStock())
                .isBest(product.getIsBest())
                .build();
        return responseProduct;
    }

    @Override
    public List<ResponseProduct> getAllProduct() {
        List<Product> productList = iProductRepository.findAll();
        if (productList.isEmpty()) {
             throw new ServiceException("상품이 없습니다.",HttpStatus.NO_CONTENT);
        }
        return ResponseProduct.mapper(productList);
    }

    @Override
    public List<IProductListByCategory> getProductForCategory(String search) {
        List<IProductListByCategory> productList = iProductRepository.findAllProductType(search);
        if (productList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.",HttpStatus.NO_CONTENT);
        }
        return productList;
    }

    @Override
    public List<IProductSearch> getSearchProduct(String search) {
        List<IProductSearch> productList = iProductRepository.findProduct(search);
        if (productList.isEmpty()) {
            throw new ServiceException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT);
        }
        return productList;
    }
    @Override
    public Page<Product> getList(Pageable pageable){
        Page<Product> paging = iProductRepository.findAll(pageable);
        return paging;

    }

    @Override

    public Long updateProduct(Long ProductId, RequestProduct requestProduct) {
        Product product = iProductRepository.findById(requestProduct.getId()).orElseThrow(
                () -> new ServiceException("찾으려는 ID의 상품이 없습니다", HttpStatus.NO_CONTENT));
        product.updateProduct(requestProduct.getName(),
                requestProduct.getPrice(),
                requestProduct.getDescription(),
                requestProduct.getStock(),
                requestProduct.getLikeCount(),
                requestProduct.getThumbnailUrl(),
                requestProduct.getIsBest()
                );
        iProductRepository.save(product);
        return product.getId();
    }

    @Override
    public void deleteProduct(Long ProductId) {
        iProductRepository.deleteById(ProductId);
    }
}
