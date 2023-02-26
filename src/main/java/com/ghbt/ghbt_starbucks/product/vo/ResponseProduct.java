package com.ghbt.ghbt_starbucks.product.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct {
    private Long id;
    private String name;
    private Integer price;
    private String description;

}
