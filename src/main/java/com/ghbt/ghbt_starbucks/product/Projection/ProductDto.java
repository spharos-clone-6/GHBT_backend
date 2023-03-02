package com.ghbt.ghbt_starbucks.product.Projection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private Integer price;

}
