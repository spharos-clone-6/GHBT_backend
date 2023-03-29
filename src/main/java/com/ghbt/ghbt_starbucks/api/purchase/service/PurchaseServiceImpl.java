package com.ghbt.ghbt_starbucks.api.purchase.service;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
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

    public void startPayment(RequestPurchase requestPurchase, User user) {
        if (requestPurchase.getPaymentType().equals(KAKAO_PAY)) {
            kakaoApi(requestPurchase, user);
        } else if (requestPurchase.getPaymentType().equals(STARBUCKS_CARD)) {
            starbucksApi(requestPurchase, user);
        } else {
            throw new ServiceException("지원하지 않는 결제 방식입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public void startPaymentFromCart(RequestPurchases requestPurchases, User user) {
        if (requestPurchases.getPaymentType().equals(KAKAO_PAY)) {
            kakaoApiFromCart(requestPurchases, user);
        } else if (requestPurchases.getPaymentType().equals(STARBUCKS_CARD)) {
            starbucksApiFromCart(requestPurchases, user);
        } else {
            throw new ServiceException("지원하지 않는 결제 방식입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void kakaoApi(RequestPurchase requestPurchase, User user) {
        IProductDetail productDetail = iSearchCategoryRepository.getOneProductId(requestPurchase.getProductId());
        log.info(productDetail.getStock().toString());
        if (productDetail.getStock() < requestPurchase.getProductQuantity()) {
            throw new ServiceException("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
        }
        UUID uuid = UUID.randomUUID();

        //저장로직으로 한번 저장 / shipping을 process로 변경해서 결제 진행중으로 / 결제 완료 / 이후 배송
        Purchase purchase = Purchase.builder()
            .uuid(uuid.toString())
            .shippingAddress(requestPurchase.getShippingAddress())
            .processStatus(ProcessStatus.PAYMENT_INCOMPLETE)
            .productId(requestPurchase.getProductId())
            .productName(requestPurchase.getProductName())
            .price(requestPurchase.getProductPrice())
            .quantity(requestPurchase.getProductQuantity())
            .totalPrice(requestPurchase.getTotalPrice())
            .user(user)
            .build();
        iPurchaseRepository.save(purchase);

        KakaoPayOrderDto kakaoPayOrderDto = KakaoPayOrderDto.toKakaoOrder(requestPurchase, uuid, user.getId());
        kakaoPayService.kakaoPayReady(kakaoPayOrderDto);
    }

    @Transactional
    public void kakaoApiFromCart(RequestPurchases requestPurchases, User user) {

        List<RequestCarts> requestCarts = requestPurchases.getPurchaseList();
        UUID uuid = UUID.randomUUID();
        for (int i = 0; i < requestCarts.size(); i++) {
            IProductDetail productDetail = iSearchCategoryRepository.getOneProductId(
                requestCarts.get(i).getProductId());
            System.out.println(
                "requestCarts = " + iSearchCategoryRepository.getOneProductId(requestCarts.get(i).getProductId()));
            if (productDetail.getStock() < requestPurchases.getPurchaseList().get(i).getProductQuantity()) {
                throw new ServiceException("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
            }

            Purchase purchase = Purchase.builder()
                .uuid(uuid.toString())
                .shippingAddress(requestPurchases.getShippingAddress())
                .processStatus(ProcessStatus.PAYMENT_INCOMPLETE)
                .productId(requestPurchases.getPurchaseList().get(i).getProductId())
                .productName(requestPurchases.getPurchaseList().get(i).getProductName())
                .price(requestPurchases.getPurchaseList().get(i).getProductPrice())
                .quantity(requestPurchases.getPurchaseList().get(i).getProductQuantity())
                .totalPrice(requestPurchases.getTotalPrice())
                .user(user)
                .build();
            iPurchaseRepository.save(purchase);

        }
        KakaoPayOrderDto kakaoPayOrderDto = KakaoPayOrderDto.toKakaoOrderFromCart(requestPurchases, uuid,
            user.getId());
        kakaoPayService.kakaoPayReady(kakaoPayOrderDto);
    }

    private void starbucksApi(RequestPurchase requestPurchase, User user) {
        log.info("아직 지원하지 않는 서비스입니다.");
    }

    private void starbucksApiFromCart(RequestPurchases requestPurchases, User user) {
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
