package com.ghbt.ghbt_starbucks.api.mobile_card.dto;

import com.ghbt.ghbt_starbucks.api.mobile_card.model.MobileCard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMobileCard {

    private Long id;
    private String name;
    private Long price;
    private String thumbnailUrl;

    public static ResponseMobileCard from(MobileCard mobileCard) {
        return ResponseMobileCard.builder()
            .id(mobileCard.getId())
            .name(mobileCard.getName())
            .price(mobileCard.getPrice())
            .thumbnailUrl(mobileCard.getThumbnailUrl())
            .build();
    }
}
