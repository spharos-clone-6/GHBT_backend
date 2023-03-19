package com.ghbt.ghbt_starbucks.api.starbucks_card.service;

import static com.ghbt.ghbt_starbucks.global.error.ErrorCode.*;

import com.ghbt.ghbt_starbucks.api.starbucks_card.dto.RequestEnrollStarbucksCard;
import com.ghbt.ghbt_starbucks.api.starbucks_card.dto.ResponseStarbucksCard;
import com.ghbt.ghbt_starbucks.api.starbucks_card.model.CardType;
import com.ghbt.ghbt_starbucks.api.starbucks_card.model.StarbucksCard;
import com.ghbt.ghbt_starbucks.api.starbucks_card.repository.IStarbucksCardRepository;
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
public class StarbucksCardServiceImpl implements IStarbucksCardService {

    private final IStarbucksCardRepository IStarbucksCardRepository;

    @Override
    public List<ResponseStarbucksCard> getAllMobileCard() {
        List<StarbucksCard> starbucksCards = IStarbucksCardRepository.findAll();
        if (starbucksCards.isEmpty()) {
            throw new ServiceException(NOT_FOUND_MOBILE_CARD.getMessage(), NOT_FOUND_MOBILE_CARD.getHttpStatus());
        }
        return starbucksCards.stream()
            .map(ResponseStarbucksCard::from)
            .collect(Collectors.toList());
    }

    @Override
    public ResponseStarbucksCard getOneMobileCard(Long mobileCardId) {
        StarbucksCard findStarbucksCard = IStarbucksCardRepository.findById(mobileCardId)
            .orElseThrow(
                () -> new ServiceException(NOT_FOUND_MOBILE_CARD.getMessage(), NOT_FOUND_MOBILE_CARD.getHttpStatus()));

        return ResponseStarbucksCard.from(findStarbucksCard);
    }

    @Override
    @Transactional
    public Long enrollMobileCard(RequestEnrollStarbucksCard requestEnrollStarbucksCard) {
        CardType cardType = encryptCardType(requestEnrollStarbucksCard.getCardNumber(),
            requestEnrollStarbucksCard.getPinNumber());
        StarbucksCard starbucksCard = StarbucksCard.toEntity(requestEnrollStarbucksCard, cardType);
        StarbucksCard savedStarbucksCard = IStarbucksCardRepository.save(starbucksCard);
        return savedStarbucksCard.getId();
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
