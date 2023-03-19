package com.ghbt.ghbt_starbucks.api.user_has_starbuckscard.model;

import com.ghbt.ghbt_starbucks.api.starbucks_card.model.StarbucksCard;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_starbuckscard.dto.RequestChargeStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbuckscard.dto.RequestStarbucksCard;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserHasStarbucksCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_nick_name")
    private String cardNickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "starbucks_card_id")
    private StarbucksCard starbucksCard;

    @Column(name = "price")
    private Long price;

    public static UserHasStarbucksCard enrollStarbucksCard(User loginUser, RequestStarbucksCard requestStarbucksCard,
        StarbucksCard findStarbucksCard) {
        return UserHasStarbucksCard.builder()
            .user(loginUser)
            .starbucksCard(findStarbucksCard)
            .cardNickName(requestStarbucksCard.getCardNickName())
            .price(findStarbucksCard.getCardType().getPrice())
            .build();
    }

    public UserHasStarbucksCard chargeCash(RequestChargeStarbucksCard requestChargeStarbucksCard) {
        this.price += requestChargeStarbucksCard.getCash();
        return this;
    }
}
