package com.ghbt.ghbt_starbucks.product.productDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;
@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;

}
