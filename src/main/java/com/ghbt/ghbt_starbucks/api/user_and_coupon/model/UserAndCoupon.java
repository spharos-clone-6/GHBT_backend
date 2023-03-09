package com.ghbt.ghbt_starbucks.api.user_and_coupon.model;

import com.ghbt.ghbt_starbucks.api.coupon.model.Coupon;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAndCoupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_unique_number")
    private String couponUniqueNumber;

    @ManyToOne
    private User user;

    @ManyToOne
    private Coupon coupon;

}
