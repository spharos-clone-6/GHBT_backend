package com.ghbt.ghbt_starbucks.api.mobilecard.repository;

import com.ghbt.ghbt_starbucks.api.mobilecard.model.MobileCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileCardRepository extends JpaRepository<MobileCard, Long> {

    Optional<MobileCard> findByPinNumberAndCardNumber(String pinNumber, String CardNumber);
}
