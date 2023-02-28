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
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseServiceImpl implements IPurchaseService {

    @Autowired
    private final IPurchaseRepository iPurchaseRepository;

    @Autowired
    private final IUserRepository iUserRepository;


    @Override
    @Transactional
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

    }

    @Override
    public ResponsePurchase getPurchaseById(Long id) {

        Purchase purchase =  iPurchaseRepository.findById(id).get();
        ResponsePurchase responsePurchase = ResponsePurchase.builder()
                .quantity(purchase.getQuantity())
                .purchaseGroup(purchase.getPurchaseGroup())
                .shippingStatus((purchase.getShippingStatus()))
                .shippingAddress(purchase.getShippingAddress())
                .productId(purchase.getProductId())
                .productName(purchase.getProductName())
                .price((purchase.getPrice()))
                .build();
        return responsePurchase;
    }

    @Override
    public List<Purchase> getAllPurchaseByUserId(Long userId){

        List<Purchase> purchaseList = iPurchaseRepository.findAllByUser_Id(userId);
        return purchaseList;
    }

    @Override
    @Transactional
    public Long updatePurchase(RequestPurchase requestPurchase, Long id){
        Purchase purchase = iPurchaseRepository.findById(id).get();

        purchase.setShippingAddress(requestPurchase.getShippingAddress());

        return purchase.getId();
    }

}
