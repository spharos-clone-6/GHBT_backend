package com.ghbt.ghbt_starbucks.api.purchase.model;

import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchases;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.utility.BaseTimeEntity;
import java.util.UUID;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class Purchase extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

//    @Column(name = "purchase_group")
//    private String purchaseGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "process_status")
    private ProcessStatus processStatus;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "uuid")
    private String uuid;

    public static Purchase toEntity(int i, RequestPurchases requestPurchases, User user, UUID uuid) {
        return Purchase.builder()
            .uuid(uuid.toString())
            .shippingAddress(requestPurchases.getShippingAddress())
            .processStatus(ProcessStatus.PAYMENT_INCOMPLETE)
            .productId(requestPurchases.getPurchaseList().get(i).getProductId())
            .productName(requestPurchases.getPurchaseList().get(i).getProductName())
            .price(requestPurchases.getPurchaseList().get(i).getProductPrice())
            .quantity(requestPurchases.getPurchaseList().get(i).getProductQuantity())
            .totalPrice(requestPurchases.getTotalPrice())
            .user(user)
            .build();
    }

}

