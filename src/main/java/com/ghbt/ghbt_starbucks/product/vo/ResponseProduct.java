package com.ghbt.ghbt_starbucks.product.vo;


import com.ghbt.ghbt_starbucks.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private Integer stock;

    public static List<ResponseProduct> mapper(List<Product> products){
        List<ResponseProduct> responseProducts = new ArrayList<>();

        for (Product product: products) {
            responseProducts.add(ResponseProduct.builder()
                    .id(product.getId()).name(product.getName())
                    .price(product.getPrice()).description(product.getDescription())
                    .stock(product.getStock()).build());
        }
        return responseProducts;
    }
}

