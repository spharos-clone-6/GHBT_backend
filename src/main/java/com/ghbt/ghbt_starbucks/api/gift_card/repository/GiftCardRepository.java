package com.ghbt.ghbt_starbucks.api.gift_card.repository;

import com.ghbt.ghbt_starbucks.api.gift_card.model.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {
}
