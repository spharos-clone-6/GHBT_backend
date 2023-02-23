package com.ghbt.ghbt_starbucks.gift_card_list.repository;

import com.ghbt.ghbt_starbucks.gift_card_list.model.GiftCardList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCardListRepository extends JpaRepository<GiftCardList, Long> {
    
}
