package com.ghbt.ghbt_starbucks.api.mobilecard.service;

import com.ghbt.ghbt_starbucks.api.mobilecard.dto.ResponseMobileCard;
import java.util.List;

public interface IMobileCardService {

    List<ResponseMobileCard> getAllMobileCard();

    ResponseMobileCard getOneMobileCard(Long mobileCardId);

}
