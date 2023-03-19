package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.model;

import com.ghbt.ghbt_starbucks.api.mobile_card.model.MobileCard;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestChargeMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestMobileCard;
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
public class UserHasMobileCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_nick_name")
    private String cardNickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobile_card_id")
    private MobileCard mobileCard;

    @Column(name = "price")
    private Long price;

    public static UserHasMobileCard enrollMobileCard(User loginUser, RequestMobileCard requestMobileCard,
        MobileCard findMobileCard) {
        return UserHasMobileCard.builder()
            .user(loginUser)
            .mobileCard(findMobileCard)
            .cardNickName(requestMobileCard.getCardNickName())
            .price(findMobileCard.getCardType().getPrice())
            .build();
    }

    public UserHasMobileCard chargeCash(RequestChargeMobileCard requestChargeMobileCard) {
        this.price += requestChargeMobileCard.getCash();
        return this;
    }
}
