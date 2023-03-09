package com.ghbt.ghbt_starbucks.api.product_like.model;

import com.ghbt.ghbt_starbucks.api.product.model.Product;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductLike {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private User user;

        @ManyToOne
        private Product product;

}
