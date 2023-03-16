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
public class ResponseMobileCardAndUser {

    private Long id;
    private String nickName;
    private String userName;

    private String cardName;
    private Long price;
    private String cardNumber;
    private String thumbnailUrl;

    public static ResponseMobileCardAndUser from(UserHasMobileCard userHasMobileCard) {
        return ResponseMobileCardAndUser.builder()
            .id(userHasMobileCard.getId())
            .nickName(userHasMobileCard.getCardName())
            .userName(userHasMobileCard.getUser().getName())
            .cardName(userHasMobileCard.getMobileCard().getName())
            .price(userHasMobileCard.getMobileCard().getPrice())
            .cardNumber(userHasMobileCard.getMobileCard().getCardNumber())
            .thumbnailUrl(userHasMobileCard.getMobileCard().getThumbnailUrl())
            .build();
    }
}
