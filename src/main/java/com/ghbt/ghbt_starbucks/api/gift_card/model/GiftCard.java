package com.ghbt.ghbt_starbucks.api.gift_card.model;

import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GiftCard extends BaseTimeEntity {
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
