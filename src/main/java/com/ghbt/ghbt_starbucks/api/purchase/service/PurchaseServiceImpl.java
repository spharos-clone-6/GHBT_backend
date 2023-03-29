package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoReadyResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestCarts;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPayResult;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchases;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.model.ProcessStatus;
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
@Transactional
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

    public KakaoReadyResponse startPayment(RequestPurchases requestPurchases, User user) {
        log.info("=======================================================");
        log.info("결제 타입 " + requestPurchases.getPaymentType().toString());
        log.info("쿠폰 아이디 " + requestPurchases.getCouponId().toString());
        log.info("쿠폰 가격 " + requestPurchases.getCouponPrice().toString());
        log.info("전체 가격 " + requestPurchases.getTotalPrice().toString());
        log.info("현금 영수증 " + requestPurchases.getCashReceipts().toString());
        log.info("배송지 " + requestPurchases.getShippingAddress().toString());
        log.info("배송 가격 " + requestPurchases.getShippingPrice().toString());
        log.info("구매목록 이름 (첫번째 항목) " + requestPurchases.getPurchaseList().get(0).getProductName());
        log.info("=======================================================");

        if (requestPurchases.getPaymentType().equals(KAKAO_PAY)) {
            KakaoReadyResponse kakaoReadyResponse = kakaoApi(requestPurchases, user);
            return kakaoReadyResponse;
        } else if (requestPurchases.getPaymentType().equals(STARBUCKS_CARD)) {
            starbucksApi(requestPurchases, user);
            return null;
        } else {
            throw new ServiceException("지원하지 않는 결제 방식입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public KakaoReadyResponse kakaoApi(RequestPurchases requestPurchases, User user) {

        List<RequestCarts> requestCarts = requestPurchases.getPurchaseList();
        UUID uuid = UUID.randomUUID();
        for (int i = 0; i < requestCarts.size(); i++) {
            IProductDetail productDetail = iSearchCategoryRepository.getOneProductId(
                requestCarts.get(i).getProductId());
            System.out.println("requestCarts = " + iSearchCategoryRepository.getOneProductId(requestCarts.get(i).getProductId()));
            if (productDetail.getStock() < requestPurchases.getPurchaseList().get(i).getProductQuantity()) {
                throw new ServiceException("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
            }
            iPurchaseRepository.save(Purchase.toEntity(i, requestPurchases, user, uuid));
        }
        KakaoPayOrderDto kakaoPayOrderDto = KakaoPayOrderDto.toKakaoOrder(requestPurchases, uuid, user.getId());
        return kakaoPayService.kakaoPayReady(kakaoPayOrderDto);
    }

    private void starbucksApi(RequestPurchases requestPurchase, User user) {
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

    public void updateProcess(RequestPayResult requestPayResult, User user) {
        List<Purchase> purchase = iPurchaseRepository.findAllByUuid(requestPayResult.getPartner_order_id());
        purchase.forEach(purchaseStatus -> {
            purchaseStatus.setProcessStatus(ProcessStatus.PAYMENT_COMPLETE);
        });
    }
}
