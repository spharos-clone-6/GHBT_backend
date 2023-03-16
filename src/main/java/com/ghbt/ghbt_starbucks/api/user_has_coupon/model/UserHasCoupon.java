package com.ghbt.ghbt_starbucks.api.user_has_coupon.model;

import com.ghbt.ghbt_starbucks.api.coupon.model.Coupon;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserHasCoupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

}
