package com.ghbt.ghbt_starbucks.api.purchase.dto;


import com.ghbt.ghbt_starbucks.api.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.dto.ResponseUserHasCoupon;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.ResponseStarbucksCard;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseBill {

    private List<ResponseUserHasCoupon> userHasCouponList;

    private List<ResponseShippingAddress> userShippingAddressList;

    private List<ResponseStarbucksCard> userStarbucksCardList;

}
