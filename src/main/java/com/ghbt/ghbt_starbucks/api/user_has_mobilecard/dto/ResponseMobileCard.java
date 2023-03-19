package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto;

import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.model.UserHasMobileCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMobileCard {

    private Long id;
    private Long price;
    private String cardName;
    private String cardNickName;
    private String thumbnailUrl;

    public static ResponseMobileCard from(UserHasMobileCard userHasMobileCard) {
        return ResponseMobileCard.builder()
            .id(userHasMobileCard.getId())
            .price(userHasMobileCard.getPrice())
            .cardName(userHasMobileCard.getStarbucksCard().getCardName())
            .cardNickName(userHasMobileCard.getCardNickName())
            .thumbnailUrl(userHasMobileCard.getStarbucksCard().getThumbnailUrl())
            .build();
    }
}
