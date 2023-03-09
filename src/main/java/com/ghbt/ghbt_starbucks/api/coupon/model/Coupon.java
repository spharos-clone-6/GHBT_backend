package com.ghbt.ghbt_starbucks.api.coupon.model;
import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "discount_price", nullable = false, length = 255)
    private Integer discountPrice;

    @Column(name = "coupon_type", nullable = false, length = 255)
    private Integer couponType;

}
