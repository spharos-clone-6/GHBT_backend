package com.ghbt.ghbt_starbucks.api.purchase.dto;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.Amount;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPayResult {

    private String aid;

    private String tid;

    private String cid;

    private String sid;

    private String partner_order_id;

    private Long partner_user_id;

    private String payment_method_type;

    private Amount amount;

    private String item_name;

    private String item_code;

    private Long quantity;

    private LocalDateTime created_at;

    private LocalDateTime approved_at;

    private String payload;

}
