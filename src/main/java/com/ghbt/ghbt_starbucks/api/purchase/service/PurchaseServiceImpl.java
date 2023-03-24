package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.cart.service.ICartService;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.repository.IPurchaseRepository;
import com.ghbt.ghbt_starbucks.api.shipping_address.service.IShippingAddressService;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.service.IUserHasCouponService;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.service.IUserHasStarbucksCardService;
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

    private final IUserHasCouponService iUserHasCouponService;

    private final IUserHasStarbucksCardService iUserHasStarbucksCardService;

    private final IShippingAddressService iShippingAddressService;

    private final ICartService iCartService;

    private final KakaoPayService kakaoPayService;

    @Override
    @Transactional
    public Long addPurchase(RequestPurchase requestPurchase, User user) {

        UUID uuid = UUID.randomUUID();
        KakaoPayOrderDto kakaoPayOrderDto = KakaoPayOrderDto.toKakaoOrder(requestPurchase, uuid, user.getId());
        kakaoPayService.kakaoPayReady(kakaoPayOrderDto);

//        Purchase purchase = Purchase.builder()
//            .user(user)
//            .quantity(requestPurchase.getQuantity())
//            .shippingAddress(requestPurchase.getShippingAddress())
//            .productId(requestPurchase.getProductId())
//            .productName(requestPurchase.getProductName())
//            .price((requestPurchase.getPrice()))
//            .uuid(uuid.toString())
//            .build();

//        Purchase savedPurchase = iPurchaseRepository.save(purchase);
//        return savedPurchase.getId();
        return null;

    }

//  장바구니를 통한 구매를 위해서 임시 제작 03-24
//    @Override
//    @Transactional
//    public Long addPurchases(RequestPurchase requestPurchase, User user) {
//        iCartService.getAllCartByUserId(user.getId());
//        return null;
//    }

    @Override
    public ResponsePurchase getPurchaseById(Long id) {

        Purchase purchase = iPurchaseRepository.findById(id).orElseThrow(
            () -> new ServiceException("요청하신 주문내역은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        return ResponsePurchase.builder()
            .quantity(purchase.getQuantity())
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

    @Override
    public ResponseBill getBill(User user) {
        iUserHasCouponService.getALLCouponByUserId(user);
        iUserHasStarbucksCardService.getUserStarbucksCards(user.getId());
        iShippingAddressService.getAllShippingAddress(user);
        return ResponseBill.builder()
            .userHasCouponList(iUserHasCouponService.getALLCouponByUserId(user))
            .userStarbucksCardList(iUserHasStarbucksCardService.getUserStarbucksCards(user.getId()))
            .userShippingAddressList(iShippingAddressService.getAllShippingAddress(user))
            .build();
    }
}
