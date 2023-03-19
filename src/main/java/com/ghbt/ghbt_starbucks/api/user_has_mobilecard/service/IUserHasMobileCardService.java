package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.service;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestChargeMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.ResponseMobileCard;
import java.util.List;

public interface IUserHasMobileCardService {

    //유저의 모바일 카드 리스트 불러오기
    List<ResponseMobileCard> getUserMobileCards(Long userId);

    //유저의 모바일 카드 단건 불러오기
    ResponseMobileCard getUserMobileCard(Long userId, Long mobileCardId);

    //유저의 모바일 카드 등록하기.
    Long saveUserMobileCard(User user, RequestMobileCard requestMobileCard);

    //모바일 카드 충전하기.
    ResponseMobileCard chargeInMobileCard(Long userId, Long mobileCardId,
        RequestChargeMobileCard requestChargeMobileCard);
}
