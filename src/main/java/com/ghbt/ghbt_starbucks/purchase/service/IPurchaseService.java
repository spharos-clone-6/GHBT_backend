package com.ghbt.ghbt_starbucks.purchase.service;

import com.ghbt.ghbt_starbucks.purchase.vo.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.vo.ResponsePurchase;

public interface IPurchaseService {

    Long addPurchase(RequestPurchase requestPurchase, Long userId);




}


