package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.service;

import com.ghbt.ghbt_starbucks.api.mobilecard.model.MobileCard;
import com.ghbt.ghbt_starbucks.api.mobilecard.repository.MobileCardRepository;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.dto.RequestMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.model.UserHasMobileCard;
import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.repository.UserHasMobileCardRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserHasMobileCardServiceImpl implements IUserHasMobileCardService {

    private final UserHasMobileCardRepository userHasMobileCardRepository;
    private final MobileCardRepository mobileCardRepository;

    @Override
    public List<UserHasMobileCard> getUserMobileCards(Long userId) {
        List<UserHasMobileCard> userHasMobileCards = userHasMobileCardRepository.findUserHasMobileCardsByUserId(userId);
        if (userHasMobileCards.isEmpty()) {
            throw new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return userHasMobileCards;
    }

    @Override
    public UserHasMobileCard getUserMobileCard(Long userId, Long mobileCardId) {
        UserHasMobileCard userHasMobileCard = userHasMobileCardRepository.findUserHasMobileCardByUserIdAndMobileCardId(
                userId, mobileCardId)
            .orElseThrow(() -> new ServiceException("등록된 모바일 상품권이 없습니다.", HttpStatus.NO_CONTENT));
        return userHasMobileCard;
    }

    @Transactional
    @Override
    public Long saveUserMobileCard(User loginUser, RequestMobileCard requestMobileCard) {
        MobileCard findMobileCard = mobileCardRepository.findByPinNumberAndCardNumber(requestMobileCard.getPinNumber(),
                requestMobileCard.getCardNumber())
            .orElseThrow(() -> new ServiceException("잘못된 상품권 등록 번호입니다.", HttpStatus.NO_CONTENT));

        UserHasMobileCard userHasMobileCard = UserHasMobileCard.toEntity(loginUser, requestMobileCard, findMobileCard);
        return userHasMobileCardRepository.save(userHasMobileCard).getId();
    }
}
