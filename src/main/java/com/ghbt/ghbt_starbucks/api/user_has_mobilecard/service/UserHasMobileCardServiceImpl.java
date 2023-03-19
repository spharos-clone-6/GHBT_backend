package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.service;

import com.ghbt.ghbt_starbucks.api.starbucks_card.model.StarbucksCard;
import com.ghbt.ghbt_starbucks.api.starbucks_card.repository.IStarbucksCardRepository;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestChargeMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.ResponseMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.model.UserHasMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.repository.UserHasMobileCardRepository;
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
public class UserHasMobileCardServiceImpl implements IUserHasMobileCardService {

    private final UserHasMobileCardRepository userHasMobileCardRepository;
    private final IStarbucksCardRepository IStarbucksCardRepository;

    @Override
    public List<ResponseMobileCard> getUserMobileCards(Long userId) {
        List<UserHasMobileCard> userHasMobileCards = userHasMobileCardRepository.findByUserId(userId);
        if (userHasMobileCards.isEmpty()) {
            throw new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return userHasMobileCards.stream()
            .map(ResponseMobileCard::from)
            .collect(Collectors.toList());
    }

    @Override
    public ResponseMobileCard getUserMobileCard(Long userId, Long mobileCardId) {
        UserHasMobileCard userHasMobileCard = userHasMobileCardRepository.findByUserIdAndMobileCardId(userId,
                mobileCardId)
            .orElseThrow(() -> new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NO_CONTENT));
        return ResponseMobileCard.from(userHasMobileCard);
    }

    @Transactional
    @Override
    public Long saveUserMobileCard(User loginUser, RequestMobileCard requestMobileCard) {
        StarbucksCard findStarbucksCard = IStarbucksCardRepository.findByPinNumberAndCardNumber(requestMobileCard.getPinNumber(),
                requestMobileCard.getCardNumber())
            .orElseThrow(() -> new ServiceException("잘못된 상품권 등록 번호입니다.", HttpStatus.NO_CONTENT));

        UserHasMobileCard userHasMobileCard = UserHasMobileCard.enrollMobileCard(loginUser, requestMobileCard,
            findStarbucksCard);
        return userHasMobileCardRepository.save(userHasMobileCard).getId();
    }

    @Transactional
    @Override
    public ResponseMobileCard chargeInMobileCard(Long userId, Long mobileCardId,
        RequestChargeMobileCard requestChargeMobileCard) {
        UserHasMobileCard findUserHasMobileCard = userHasMobileCardRepository.findByUserIdAndMobileCardId(userId,
                mobileCardId)
            .orElseThrow(() -> new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NOT_FOUND));
        UserHasMobileCard chargedUserHasMobileCard = findUserHasMobileCard.chargeCash(requestChargeMobileCard);
        return ResponseMobileCard.from(chargedUserHasMobileCard);
    }
}