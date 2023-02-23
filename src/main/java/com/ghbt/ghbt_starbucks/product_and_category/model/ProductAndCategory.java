package com.ghbt.ghbt_starbucks.product_and_category.model;

import com.ghbt.ghbt_starbucks.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAndCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category productId;

}
