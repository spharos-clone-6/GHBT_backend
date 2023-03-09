package com.ghbt.ghbt_starbucks.api.gift_card_list.model;

import com.ghbt.ghbt_starbucks.api.gift_card.model.GiftCard;
import com.ghbt.ghbt_starbucks.api.user.model.User;
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
public class GiftCardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_price")
    private Integer lastPrice;

    @Column(name = "gift_card_number",length = 255)
    private String GiftCardNumber;

    @ManyToOne
    private User user;

    @ManyToOne
    private GiftCard giftCard;
}
