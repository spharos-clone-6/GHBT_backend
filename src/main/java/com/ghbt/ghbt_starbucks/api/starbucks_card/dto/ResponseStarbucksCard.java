package com.ghbt.ghbt_starbucks.api.starbucks_card.dto;

import com.ghbt.ghbt_starbucks.api.starbucks_card.model.StarbucksCard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStarbucksCard {

    private Long id;
    private String cardName;
    private Long price;
    private String thumbnailUrl;

    public static ResponseStarbucksCard from(StarbucksCard starbucksCard) {
        return ResponseStarbucksCard.builder()
            .id(starbucksCard.getId())
            .cardName(starbucksCard.getCardName())
            .price(starbucksCard.getCardType().getPrice())
            .thumbnailUrl(starbucksCard.getThumbnailUrl())
            .build();
    }
}
