package com.ghbt.ghbt_starbucks.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RequestProduct {

    private String name;

    private Integer price;

    private String description;

}
