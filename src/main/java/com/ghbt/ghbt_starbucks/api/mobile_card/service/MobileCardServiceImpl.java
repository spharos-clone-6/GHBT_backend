package com.ghbt.ghbt_starbucks.api.mobile_card.service;

import static com.ghbt.ghbt_starbucks.global.error.ErrorCode.*;

import com.ghbt.ghbt_starbucks.api.mobile_card.dto.RequestMobileCardToEnroll;
import com.ghbt.ghbt_starbucks.api.mobile_card.dto.ResponseMobileCard;
import com.ghbt.ghbt_starbucks.api.mobile_card.model.MobileCard;
import com.ghbt.ghbt_starbucks.api.mobile_card.repository.MobileCardRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MobileCardServiceImpl implements IMobileCardService {

    private final MobileCardRepository mobileCardRepository;

    @Override
    public List<ResponseMobileCard> getAllMobileCard() {
        List<MobileCard> mobileCards = mobileCardRepository.findAll();
        if (mobileCards.isEmpty()) {
            throw new ServiceException(NOT_FOUND_MOBILE_CARD.getMessage(), NOT_FOUND_MOBILE_CARD.getHttpStatus());
        }
        return mobileCards.stream()
            .map(ResponseMobileCard::from)
            .collect(Collectors.toList());
    }

    @Override
    public ResponseMobileCard getOneMobileCard(Long mobileCardId) {
        MobileCard findMobileCard = mobileCardRepository.findById(mobileCardId)
            .orElseThrow(() -> new ServiceException(NOT_FOUND_MOBILE_CARD.getMessage(), NOT_FOUND_MOBILE_CARD.getHttpStatus()));

        return ResponseMobileCard.from(findMobileCard);
    }

    @Override
    @Transactional
    public Long enrollMobileCard(RequestMobileCardToEnroll requestMobileCardToEnroll) {
        MobileCard mobileCard = MobileCard.toEntity(requestMobileCardToEnroll);
        MobileCard savedMobileCard = mobileCardRepository.save(mobileCard);
        return savedMobileCard.getId();
    }
}
