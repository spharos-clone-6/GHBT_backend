package com.ghbt.ghbt_starbucks.purchase.service;

import com.ghbt.ghbt_starbucks.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.dto.ResponsePurchase;
import java.util.List;

public interface IPurchaseService {

    Long addPurchase(RequestPurchase requestPurchase, Long userId);

    ResponsePurchase getPurchaseById(Long id);

    List<ResponsePurchase> getAllPurchaseByUserId(Long userId);

    Long updatePurchase(RequestPurchase requestPurchase, Long id);


}


