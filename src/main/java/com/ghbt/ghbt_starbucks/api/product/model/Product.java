package com.ghbt.ghbt_starbucks.api.product.model;

import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Builder
@Getter
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

    @Column(nullable = false, length = 1000)
    private String thumbnailUrl;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isBest;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isNew;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;


    @PrePersist
    public void prePersist() {
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
        this.isBest = this.isBest != null && this.isBest;
        this.isNew = this.isNew != null && this.isNew;
    }

    public void updateProduct(String name, Integer price, String description, Integer stock, Integer likeCount,
        String thumbnailUrl, Boolean isBest) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.likeCount = likeCount;
        this.thumbnailUrl = thumbnailUrl;
        this.isBest = isBest;
    }
}

