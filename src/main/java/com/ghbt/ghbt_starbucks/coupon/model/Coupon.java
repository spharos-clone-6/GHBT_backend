package com.ghbt.ghbt_starbucks.coupon.model;

import com.ghbt.ghbt_starbucks.utility.BaseTimeEntity;
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
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "discount_price", nullable = false, length = 255)
    private Boolean discountPrice;

    @Column(name = "is_free", nullable = false, length = 255)
    private Integer isFree;
}
