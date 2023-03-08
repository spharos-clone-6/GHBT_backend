package com.ghbt.ghbt_starbucks.user_and_coupon.model;


import com.ghbt.ghbt_starbucks.coupon.model.Coupon;
import com.ghbt.ghbt_starbucks.user.model.User;
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
public class UserAndCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_unique_number",length = 255)
    private String couponUniqueNumber;

    @ManyToOne
    private User user;

    @ManyToOne
    private Coupon coupon;
}
