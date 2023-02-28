package com.ghbt.ghbt_starbucks.purchase.service;

import com.ghbt.ghbt_starbucks.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.purchase.vo.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.vo.ResponsePurchase;
import java.util.List;

public interface IPurchaseService {

    Long addPurchase(RequestPurchase requestPurchase, Long userId);

    ResponsePurchase getPurchaseById(Long id);

    List<Purchase> getAllPurchaseByUserId(Long userId);

    Long updatePurchase(RequestPurchase requestPurchase, Long id);


}


