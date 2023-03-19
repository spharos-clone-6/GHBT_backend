package com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto;

import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.model.UserHasStarbucksCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStarbucksCard {

    private Long id;
    private Long price;
    private String cardName;
    private String cardNickName;
    private String thumbnailUrl;

    public static ResponseStarbucksCard from(UserHasStarbucksCard userHasStarbucksCard) {
        return ResponseStarbucksCard.builder()
            .id(userHasStarbucksCard.getId())
            .price(userHasStarbucksCard.getPrice())
            .cardName(userHasStarbucksCard.getStarbucksCard().getCardName())
            .cardNickName(userHasStarbucksCard.getCardNickName())
            .thumbnailUrl(userHasStarbucksCard.getStarbucksCard().getThumbnailUrl())
            .build();
    }
}
