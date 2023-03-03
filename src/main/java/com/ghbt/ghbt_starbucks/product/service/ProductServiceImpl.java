package com.ghbt.ghbt_starbucks.product.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.repository.ICategoryRepository;
import com.ghbt.ghbt_starbucks.product.Projection.IProductSearch;
import com.ghbt.ghbt_starbucks.product.model.Product;
import com.ghbt.ghbt_starbucks.product.Projection.IProductListByCategory;
import com.ghbt.ghbt_starbucks.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.product.vo.ResponseProduct;
import com.ghbt.ghbt_starbucks.product_and_category.model.ProductAndCategory;
import com.ghbt.ghbt_starbucks.product_and_category.repository.IProductAndCategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .build();
        Product resProduct = iProductRepository.save(product);

        for (String cate :requestProduct.getCategoryList()) {
            System.out.println(cate);
            Category category= iCategoryRepository.findByName(cate);
            System.out.println(category);
            ProductAndCategory productAndCategory = ProductAndCategory.builder()
                    .productId(resProduct)
                    .categoryId(category)
                    .build();
            System.out.println(productAndCategory);
            ProductAndCategory resProductAndCategory = iProductAndCategoryRepository.save(productAndCategory);
        }



        ResponseProduct responseProduct = ResponseProduct.builder()
                .id(resProduct.getId())
                .name(resProduct.getName())
                .price(resProduct.getPrice())
                .description(resProduct.getDescription())
                .build();
        return responseProduct;
    }
    @Override
    public ResponseProduct getProduct(Long id) {
        Optional<Product> getProduct =iProductRepository.findById(id);
        // or
        Product product = iProductRepository.findById(id).get();
        ResponseProduct responseProduct = ResponseProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
        return responseProduct;
    }

    @Override
    public List<Product> getAllProduct() {

        List<Product> productList = iProductRepository.findAll();
        return productList;
    }

    @Override
    public List<IProductListByCategory> getProductForCategory(String search) {
        List<IProductListByCategory> productList = iProductRepository.findAllProductType(search);
        return productList;
    }

    @Override
    public List<IProductSearch> getSearchProduct(String search) {
        List<IProductSearch> productList = iProductRepository.findProduct(search);
        return productList;
    }
    @Override
    public Page<Product> getList(Pageable pageable){
        Page<Product> paging = iProductRepository.findAll(pageable);
        return paging;

    }
}
