package com.ghbt.ghbt_starbucks.api.user_has_starbuckscard.service;

import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_starbuckscard.dto.RequestChargeStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbuckscard.dto.RequestStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbuckscard.dto.ResponseStarbucksCard;
import java.util.List;

public interface IUserHasStarbucksCardService {

    //유저의 모바일 카드 리스트 불러오기
    List<ResponseStarbucksCard> getUserStarbucksCards(Long userId);

    //유저의 모바일 카드 단건 불러오기
    ResponseStarbucksCard getUserStarbucksCard(Long userId, Long starbucksCardId);

    //유저의 모바일 카드 등록하기.
    Long saveUserStarbucksCard(User user, RequestStarbucksCard requestStarbucksCard);

    //모바일 카드 충전하기.
    ResponseStarbucksCard chargeInStarbucksCard(Long userId, Long starbucksCardId,
        RequestChargeStarbucksCard requestChargeStarbucksCard);
}
