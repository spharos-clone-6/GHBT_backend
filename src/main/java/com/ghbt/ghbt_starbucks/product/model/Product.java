package com.ghbt.ghbt_starbucks.product.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ghbt.ghbt_starbucks.utility.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private String description;

//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date createDate;
//
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date updateDate;
}
