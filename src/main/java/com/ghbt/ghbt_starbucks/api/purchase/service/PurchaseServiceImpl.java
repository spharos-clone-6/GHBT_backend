package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.repository.IPurchaseRepository;
import com.ghbt.ghbt_starbucks.api.search_category.repository.ISearchCategoryRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseServiceImpl {

    private final String KAKAO_PAY = "kakao-pay";
    private final String STARBUCKS_CARD = "starbucks-card";

    private final IPurchaseRepository iPurchaseRepository;
    private final IUserHasCouponService iUserHasCouponService;
    private final IUserHasStarbucksCardService iUserHasStarbucksCardService;
    private final IShippingAddressService iShippingAddressService;
    private final KakaoPayService kakaoPayService;
    private final ISearchCategoryRepository iSearchCategoryRepository;

    public void startPayment(RequestPurchase requestPurchase, User user) {
        if (requestPurchase.getPaymentType().equals(KAKAO_PAY)) {
            kakaoApi(requestPurchase, user);
        } else if (requestPurchase.getPaymentType().equals(STARBUCKS_CARD)) {
            starbucksApi(requestPurchase, user);
        } else {
            throw new ServiceException("지원하지 않는 결제 방식입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public void kakaoApi(RequestPurchase requestPurchase, User user) {
        IProductDetail productDetail = iSearchCategoryRepository.getOneProductId(requestPurchase.getProductId());
        log.info(productDetail.getStock().toString());
        if (productDetail.getStock() < requestPurchase.getProductQuantity()) {
            throw new ServiceException("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
        }
        UUID uuid = UUID.randomUUID();
        KakaoPayOrderDto kakaoPayOrderDto = KakaoPayOrderDto.toKakaoOrder(requestPurchase, uuid, user.getId());
        kakaoPayService.kakaoPayReady(kakaoPayOrderDto);
    }

    private void starbucksApi(RequestPurchase requestPurchase, User user) {
        log.info("아직 지원하지 않는 서비스입니다.");
    }

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

    public List<ResponsePurchase> getAllPurchaseByUserId(User user) {
        List<Purchase> purchaseList = iPurchaseRepository.findAllByUserId(user.getId());
        if (purchaseList.isEmpty()) {
            throw new ServiceException("주문내역이 없습니다.", HttpStatus.NO_CONTENT);
        }
        return purchaseList.stream()
            .map(ResponsePurchase::from)
            .collect(Collectors.toList());
    }


    @Transactional
    public Long updatePurchase(RequestPurchase requestPurchase, Long purchaseId) {
        Purchase purchase = iPurchaseRepository.findById(purchaseId).orElseThrow(
            () -> new ServiceException("요청하신 주문은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        purchase.setShippingAddress(requestPurchase.getShippingAddress());
        return purchase.getId();
    }


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
