package com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.service;

import com.ghbt.ghbt_starbucks.api.starbucks_card.model.StarbucksCard;
import com.ghbt.ghbt_starbucks.api.starbucks_card.repository.IStarbucksCardRepository;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.RequestChargeStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.RequestStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.ResponseStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.model.UserHasStarbucksCard;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.repository.IUserHasStarbucksCardRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserHasStarbucksCardServiceImpl implements IUserHasStarbucksCardService {

    private final IUserHasStarbucksCardRepository iUserHasStarbucksCardRepository;
    private final IStarbucksCardRepository iStarbucksCardRepository;

    @Override
    public List<ResponseStarbucksCard> getUserStarbucksCards(Long userId) {
        List<UserHasStarbucksCard> userHasStarbucksCards = iUserHasStarbucksCardRepository.findByUserId(userId);
        if (userHasStarbucksCards.isEmpty()) {
            throw new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return userHasStarbucksCards.stream()
            .map(ResponseStarbucksCard::from)
            .collect(Collectors.toList());
    }

    @Override
    public ResponseStarbucksCard getUserStarbucksCard(Long userId, Long starbucksCardId) {
        UserHasStarbucksCard userHasStarbucksCard = iUserHasStarbucksCardRepository.findByUserIdAndStarbucksCardId(userId,
                starbucksCardId)
            .orElseThrow(() -> new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NO_CONTENT));
        return ResponseStarbucksCard.from(userHasStarbucksCard);
    }

    @Transactional
    @Override
    public Long saveUserStarbucksCard(User loginUser, RequestStarbucksCard requestStarbucksCard) {
        StarbucksCard findStarbucksCard = iStarbucksCardRepository.findByPinNumberAndCardNumber(requestStarbucksCard.getPinNumber(),
                requestStarbucksCard.getCardNumber())
            .orElseThrow(() -> new ServiceException("잘못된 상품권 등록 번호입니다.", HttpStatus.NO_CONTENT));

        UserHasStarbucksCard userHasStarbucksCard = UserHasStarbucksCard.enrollStarbucksCard(loginUser, requestStarbucksCard,
            findStarbucksCard);
        return iUserHasStarbucksCardRepository.save(userHasStarbucksCard).getId();
    }

    @Transactional
    @Override
    public ResponseStarbucksCard chargeStarbucksCard(Long userId, Long starbucksCardId,
        RequestChargeStarbucksCard requestChargeStarbucksCard) {
        UserHasStarbucksCard findUserHasStarbucksCard = iUserHasStarbucksCardRepository.findByUserIdAndStarbucksCardId(userId,
                starbucksCardId)
            .orElseThrow(() -> new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NOT_FOUND));
        UserHasStarbucksCard chargedUserHasStarbucksCard = findUserHasStarbucksCard.chargeCash(requestChargeStarbucksCard);
        return ResponseStarbucksCard.from(chargedUserHasStarbucksCard);
    }
}