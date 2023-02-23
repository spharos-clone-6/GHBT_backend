package com.ghbt.ghbt_starbucks.purchase.model;

import com.ghbt.ghbt_starbucks.user.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "purchase_group")
    private String purchaseGroup;

    @Column(name= "shipping_status")
    private ShippingStatus shippingStatus;

    @Column(name="shipping_address")
    private String shippingAddress;

    @Column(name="product_id")
    private String productId;

    @Column(name="product_name")
    private String productName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "uuid")
    private String uuid;










}
