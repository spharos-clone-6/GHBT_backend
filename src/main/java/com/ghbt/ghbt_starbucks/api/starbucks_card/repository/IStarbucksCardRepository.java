package com.ghbt.ghbt_starbucks.api.starbucks_card.repository;

import com.ghbt.ghbt_starbucks.api.starbucks_card.model.StarbucksCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStarbucksCardRepository extends JpaRepository<StarbucksCard, Long> {

    Optional<StarbucksCard> findByPinNumberAndCardNumber(String pinNumber, String CardNumber);
}
