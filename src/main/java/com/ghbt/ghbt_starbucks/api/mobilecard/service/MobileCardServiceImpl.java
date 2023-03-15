package com.ghbt.ghbt_starbucks.api.mobilecard.service;

import com.ghbt.ghbt_starbucks.api.mobilecard.dto.ResponseMobileCard;
import com.ghbt.ghbt_starbucks.api.mobilecard.model.MobileCard;
import com.ghbt.ghbt_starbucks.api.mobilecard.repository.MobileCardRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
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
        return mobileCards.stream()
            .map(ResponseMobileCard::from)
            .collect(Collectors.toList());
    }

    @Override
    public ResponseMobileCard getOneMobileCard(Long mobileCardId) {
        MobileCard findMobileCard = mobileCardRepository.findById(mobileCardId)
            .orElseThrow(() -> new ServiceException("존재하지 않는 모바일카드 입니다.", HttpStatus.NO_CONTENT));

        return ResponseMobileCard.from(findMobileCard);
    }
}
