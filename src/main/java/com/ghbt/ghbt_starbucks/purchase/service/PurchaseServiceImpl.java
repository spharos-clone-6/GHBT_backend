package com.ghbt.ghbt_starbucks.purchase.service;

import com.ghbt.ghbt_starbucks.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.purchase.repository.IPurchaseRepository;
import com.ghbt.ghbt_starbucks.purchase.vo.RequestPurchase;
import com.ghbt.ghbt_starbucks.purchase.vo.ResponsePurchase;
import com.ghbt.ghbt_starbucks.user.model.User;
import com.ghbt.ghbt_starbucks.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements IPurchaseService {

    @Autowired
    private final IPurchaseRepository iPurchaseRepository;

    @Autowired
    private final IUserRepository iUserRepository;


    @Override
    public Long addPurchase(RequestPurchase requestPurchase, Long userId) {

        User findUser = iUserRepository.findById(userId).get();
        UUID uuid =  UUID.randomUUID();

        Purchase purchase = Purchase.builder()
                .user(findUser)
                .quantity(requestPurchase.getQuantity())
                .purchaseGroup(requestPurchase.getPurchaseGroup())
                .shippingStatus((requestPurchase.getShippingStatus()))
                .shippingAddress(requestPurchase.getShippingAddress())
                .productId(requestPurchase.getProductId())
                .productName(requestPurchase.getProductName())
                .price((requestPurchase.getPrice()))
                .uuid(uuid.toString())
                .build();

        Purchase savedPurchase = iPurchaseRepository.save(purchase);
        return savedPurchase.getId();

//                ResponsePurchase responsePurchase = ResponsePurchase.builder()
//                        .id(requestPurchase.getId())
//                        .user(user)
//                        .productId(resPurchase.getProductId())
//                        .productName(resPurchase.getProductName())
//                        .price((resPurchase.getPrice()))
//                        .quantity(resPurchase.getQuantity())
//                        .purchaseGroup(resPurchase.getPurchaseGroup())
//                        .shippingAddress(requestPurchase.getShippingAddress())
//                        .shippingStatus(resPurchase.getShippingStatus())
//                        .build();

    }

}
