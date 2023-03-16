package com.ghbt.ghbt_starbucks.api.mobilecard.model;

import com.ghbt.ghbt_starbucks.api.mobilecard.dto.RequestMobileCardToEnroll;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity
public class MobileCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "pin_number")
    private String pinNumber;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;


    public static MobileCard toEntity(RequestMobileCardToEnroll requestMobileCardToEnroll) {
        return MobileCard.builder()
            .cardNumber(requestMobileCardToEnroll.getCardNumber())
            .name(requestMobileCardToEnroll.getName())
            .price(requestMobileCardToEnroll.getPrice())
            .thumbnailUrl(requestMobileCardToEnroll.getThumbnailUrl())
            .pinNumber(requestMobileCardToEnroll.getPinNumber())
            .build();
    }
}
