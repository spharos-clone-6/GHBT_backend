package com.ghbt.ghbt_starbucks.purchase.model;

import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.utility.BaseTimeEntity;
import lombok.*;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Data
public class Purchase extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "purchase_group", nullable = false)
    private String purchaseGroup;

    @Column(name= "shipping_status", nullable = false)
    private ShippingStatus shippingStatus;

    @Column(name="shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name="product_id", nullable = false)
    private String productId;

    @Column(name="product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "uuid")
    private String uuid;

    //nullable 전원 추가










}
