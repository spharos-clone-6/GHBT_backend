package com.ghbt.ghbt_starbucks.api.image.model;

import com.ghbt.ghbt_starbucks.api.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String altTag;

    @Column(nullable = false)
    private Integer type;
}
