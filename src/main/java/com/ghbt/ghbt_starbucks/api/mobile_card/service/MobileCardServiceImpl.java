package com.ghbt.ghbt_starbucks.api.mobile_card.service;

import static com.ghbt.ghbt_starbucks.global.error.ErrorCode.*;

import com.ghbt.ghbt_starbucks.api.mobile_card.dto.RequestEnrollMobileCard;
import com.ghbt.ghbt_starbucks.api.mobile_card.dto.ResponseMobileCard;
import com.ghbt.ghbt_starbucks.api.mobile_card.model.CardType;
import com.ghbt.ghbt_starbucks.api.mobile_card.model.MobileCard;
import com.ghbt.ghbt_starbucks.api.mobile_card.repository.MobileCardRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            .orElseThrow(
                () -> new ServiceException(NOT_FOUND_MOBILE_CARD.getMessage(), NOT_FOUND_MOBILE_CARD.getHttpStatus()));

        return ResponseMobileCard.from(findMobileCard);
    }

    @Override
    @Transactional
    public Long enrollMobileCard(RequestEnrollMobileCard requestEnrollMobileCard) {
        CardType cardType = encryptCardType(requestEnrollMobileCard.getCardNumber(),
            requestEnrollMobileCard.getPinNumber());
        MobileCard mobileCard = MobileCard.toEntity(requestEnrollMobileCard, cardType);
        MobileCard savedMobileCard = mobileCardRepository.save(mobileCard);
        return savedMobileCard.getId();
    }

    private CardType encryptCardType(String cardNumber, String pinNumber) {
        Long cardTypeKey = Long.parseLong(pinNumber) % (
            Long.parseLong(cardNumber) % 10000
                + Long.parseLong(cardNumber) / 10000 % 10000
                + Long.parseLong(cardNumber) / 10000 / 10000 % 10000
                + Long.parseLong(cardNumber) / 10000 / 10000 / 10000 % 10000
        ) % 4;
        return Arrays.stream(CardType.values())
            .filter(c -> c.getKey().equals(cardTypeKey))
            .findFirst()
            .orElseThrow(() -> new ServiceException("잘못된 카드 번호 및 pin 형식 입니다.", HttpStatus.NOT_FOUND));
    }
}
