package com.ghbt.ghbt_starbucks.api.mobile_card.model;

import com.ghbt.ghbt_starbucks.api.mobile_card.dto.RequestEnrollMobileCard;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MobileCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_name")
    private String cardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "pin_number")
    private String pinNumber;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;


    public static MobileCard toEntity(RequestEnrollMobileCard requestEnrollMobileCard, CardType cardType) {
        return MobileCard.builder()
            .cardNumber(requestEnrollMobileCard.getCardNumber())
            .pinNumber(requestEnrollMobileCard.getPinNumber())
            .cardType(cardType)
            .cardName(requestEnrollMobileCard.getCardName())
            .thumbnailUrl(requestEnrollMobileCard.getThumbnailUrl())
            .build();
    }
}
