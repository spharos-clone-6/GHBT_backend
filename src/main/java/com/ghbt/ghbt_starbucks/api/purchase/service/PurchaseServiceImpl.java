package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.purchase.repository.IPurchaseRepository;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.api.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseServiceImpl implements IPurchaseService {

    private final IPurchaseRepository iPurchaseRepository;

    @Override
    @Transactional
    public Long addPurchase(RequestPurchase requestPurchase, User user) {

        UUID uuid = UUID.randomUUID();

        Purchase purchase = Purchase.builder()
            .user(user)
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

        Purchase purchase = iPurchaseRepository.findById(id).orElseThrow(
            () -> new ServiceException("요청하신 주문내역은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        return ResponsePurchase.builder()
            .quantity(purchase.getQuantity())
            .purchaseGroup(purchase.getPurchaseGroup())
            .shippingStatus((purchase.getShippingStatus()))
            .shippingAddress(purchase.getShippingAddress())
            .productId(purchase.getProductId())
            .productName(purchase.getProductName())
            .price((purchase.getPrice()))
            .build();

    }

    @Override
    public List<ResponsePurchase> getAllPurchaseByUserId(User user) {
        List<Purchase> purchaseList = iPurchaseRepository.findAllByUserId(user.getId());
        if (purchaseList.isEmpty()) {
            throw new ServiceException("주문내역이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return purchaseList.stream()
            .map(ResponsePurchase::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long updatePurchase(RequestPurchase requestPurchase, Long purchaseId) {
        Purchase purchase = iPurchaseRepository.findById(purchaseId).orElseThrow(
            () -> new ServiceException("요청하신 주문은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        purchase.setShippingAddress(requestPurchase.getShippingAddress());
        return purchase.getId();
    }
}
