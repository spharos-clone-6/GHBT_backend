package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoReadyResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ProductDetail;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPayResult;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.model.ProcessStatus;
import com.ghbt.ghbt_starbucks.api.purchase.repository.IPurchaseRepository;
import com.ghbt.ghbt_starbucks.api.shipping_address.service.IShippingAddressService;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.service.IUserHasCouponService;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.service.IUserHasStarbucksCardService;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.api.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchaseOld;
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
@Transactional
@RequiredArgsConstructor
public class PurchaseServiceImpl {

    private final String KAKAO_PAY = "kakao-pay";
    private final String STARBUCKS_CARD = "starbucks-card";

    private final IPurchaseRepository iPurchaseRepository;
    private final IProductRepository iProductRepository;

    private final IUserHasCouponService iUserHasCouponService;
    private final IUserHasStarbucksCardService iUserHasStarbucksCardService;
    private final IShippingAddressService iShippingAddressService;
    private final KakaoPayService kakaoPayService;


    /**
     * 결제 진입점
     */
    public KakaoReadyResponse startPayment(RequestPurchase requestPurchase, User user) {
        log.info("=================");
        log.info("결제 타입 " + requestPurchase.getPaymentType());
        log.info("상품 종류 " + requestPurchase.getPurchaseList().size() + "개");
        log.info("쿠폰 가격 " + requestPurchase.getCouponPrice() + "원");
        log.info("전체 가격 " + requestPurchase.getTotalPrice() + "원");
        log.info("=================");

        if (requestPurchase.getPaymentType().equals(KAKAO_PAY)) {
            KakaoReadyResponse kakaoReadyResponse = kakaoApi(requestPurchase, user);
            return kakaoReadyResponse;
        } else if (requestPurchase.getPaymentType().equals(STARBUCKS_CARD)) {
            starbucksApi(requestPurchase, user);
            return null;
        } else {
            throw new ServiceException("지원하지 않는 결제 방식입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 카카오 결제
     */
    @Transactional
    public KakaoReadyResponse kakaoApi(RequestPurchase requestPurchase, User user) {

        UUID uuid = UUID.randomUUID();
        if (requestPurchase.getPurchaseList().stream().anyMatch(p -> getStock(p) < p.getProductQuantity())) {
            throw new ServiceException("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
        }
        requestPurchase.getPurchaseList().stream()
            .forEach(p -> iPurchaseRepository.save(Purchase.toEntity(p, requestPurchase, user, uuid)));
        KakaoPayOrderDto kakaoPayOrderDto = KakaoPayOrderDto.toKakaoOrder(requestPurchase, uuid, user.getId());
        return kakaoPayService.kakaoPayReady(kakaoPayOrderDto);
    }

    /**
     * 스타벅스 결제
     */
    private void starbucksApi(RequestPurchase requestPurchase, User user) {

        log.info("아직 지원하지 않는 서비스입니다.");

    }

    private Integer getStock(ProductDetail productDetail) {
        try {
            return iProductRepository.findById(productDetail.getProductId()).get().getStock();
        } catch (Exception e) {
            throw new ServiceException("상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponsePurchase getPurchaseById(Long id) {
        Purchase purchase = iPurchaseRepository.findById(id)
            .orElseThrow(() -> new ServiceException("요청하신 주문내역은 존재하지 않습니다", HttpStatus.NO_CONTENT));
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
    public Long updatePurchase(RequestPurchaseOld requestPurchaseOld, Long purchaseId) {
        Purchase purchase = iPurchaseRepository.findById(purchaseId).orElseThrow(
            () -> new ServiceException("요청하신 주문은 존재하지 않습니다", HttpStatus.NO_CONTENT));
        purchase.setShippingAddress(requestPurchaseOld.getShippingAddress());
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

    public void updateProcess(RequestPayResult requestPayResult, User user) {
        List<Purchase> purchase = iPurchaseRepository.findAllByUuid(requestPayResult.getPartner_order_id());
        purchase.forEach(purchaseStatus -> {
            purchaseStatus.setProcessStatus(ProcessStatus.PAYMENT_COMPLETE);
        });
    }
}
