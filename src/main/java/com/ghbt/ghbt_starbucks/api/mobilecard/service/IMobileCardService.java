package com.ghbt.ghbt_starbucks.api.mobilecard.service;

import com.ghbt.ghbt_starbucks.api.mobilecard.dto.RequestMobileCardToEnroll;
import com.ghbt.ghbt_starbucks.api.mobilecard.dto.ResponseMobileCard;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import java.util.List;

public interface IMobileCardService {

    List<ResponseMobileCard> getAllMobileCard();

    ResponseMobileCard getOneMobileCard(Long mobileCardId);

    Long enrollMobileCard(RequestMobileCardToEnroll requestMobileCardToEnroll);
}
