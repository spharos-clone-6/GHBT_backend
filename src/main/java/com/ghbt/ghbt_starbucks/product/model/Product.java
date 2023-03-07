package com.ghbt.ghbt_starbucks.product.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ghbt.ghbt_starbucks.product.vo.RequestProduct;
import com.ghbt.ghbt_starbucks.utility.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
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

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;


    @PrePersist
    public void prePersist(){
        this.likeCount = this.likeCount == null ? 0:this.likeCount;
    }
    public void updateProduct(String name, Integer price, String description, Integer stock, Integer likeCount){
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.likeCount = likeCount;
    }
    }

