package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import java.util.List;

public interface IPurchaseService {

    Long addPurchase(RequestPurchase requestPurchase, User user);

//    Long addPurchases(RequestPurchase requestPurchase, User user);

    ResponsePurchase getPurchaseById(Long id);

    List<ResponsePurchase> getAllPurchaseByUserId(User user);

    Long updatePurchase(RequestPurchase requestPurchase, Long id);

    ResponseBill getBill(User user);
}


