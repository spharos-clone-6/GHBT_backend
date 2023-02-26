package com.ghbt.ghbt_starbucks.category.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCategory {
    private Long id;
    private String name;
    private Integer type;
}
