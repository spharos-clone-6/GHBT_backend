package com.ghbt.ghbt_starbucks.purchase.service;

import com.ghbt.ghbt_starbucks.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.user.model.User;

import java.util.List;

public interface IPurchaseService {

    Long addPurchase(RequestPurchase requestPurchase, User user);

    ResponsePurchase getPurchaseById(Long id);

    List<ResponsePurchase> getAllPurchaseByUserId(User user);

    Long updatePurchase(RequestPurchase requestPurchase, Long id);


}


