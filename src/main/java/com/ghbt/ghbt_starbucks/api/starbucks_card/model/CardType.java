package com.ghbt.ghbt_starbucks.api.starbucks_card.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardType {
    TEN_THOUSAND_WON(0L, "1만원", 10000L),
    THIRTY_THOUSAND_WON(1L, "3만원", 30000L),
    FIFTY_THOUSAND_WON(2L, "5만원", 50000L),
    ONE_HUNDRED_THOUSAND_WON(3L, "10만원", 100000L),

    ;
    private Long key;
    private String name;
    private Long price;

}
