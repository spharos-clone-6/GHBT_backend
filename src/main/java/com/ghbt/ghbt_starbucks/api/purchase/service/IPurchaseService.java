package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchaseOld;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import java.util.List;

public interface IPurchaseService {

    Long addPurchase(RequestPurchaseOld requestPurchaseOld, User user);

    ResponsePurchase getPurchaseById(Long id);

    List<ResponsePurchase> getAllPurchaseByUserId(User user);

    Long updatePurchase(RequestPurchaseOld requestPurchaseOld, Long id);

    ResponseBill getBill(User user);
}


