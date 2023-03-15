package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.model;

import com.ghbt.ghbt_starbucks.api.mobilecard.model.MobileCard;
import com.ghbt.ghbt_starbucks.api.user.model.User;
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

    @Column(name = "card_name")
    private String cardName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobile_card_id")
    private MobileCard mobileCard;

    public static UserHasMobileCard toEntity(User loginUser, RequestMobileCard requestMobileCard,
        MobileCard findMobileCard) {
        return UserHasMobileCard.builder()
            .cardName(requestMobileCard.getCardName())
            .user(loginUser)
            .mobileCard(findMobileCard)
            .build();
    }
}
