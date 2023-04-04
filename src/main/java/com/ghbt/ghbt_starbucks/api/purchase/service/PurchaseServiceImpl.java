package com.ghbt.ghbt_starbucks.api.purchase.service;

import static com.ghbt.ghbt_starbucks.global.error.ErrorCode.*;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.ResponseKakaoReady;
import com.ghbt.ghbt_starbucks.api.kakaopay.service.KakaoPayService;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ProductDetail;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPayResult;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponseBill;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePayment;
import com.ghbt.ghbt_starbucks.api.purchase.model.ProcessStatus;
import com.ghbt.ghbt_starbucks.api.purchase.model.PurchaseType;
import com.ghbt.ghbt_starbucks.api.purchase.repository.IPurchaseRepository;
import com.ghbt.ghbt_starbucks.api.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.api.shipping_address.service.IShippingAddressService;
import com.ghbt.ghbt_starbucks.api.user_has_coupon.service.IUserHasCouponService;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.dto.ResponseStarbucksCardReady;
import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.service.IUserHasStarbucksCardService;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.api.purchase.model.Purchase;
import com.ghbt.ghbt_starbucks.api.purchase.dto.RequestPurchaseOld;
import com.ghbt.ghbt_starbucks.api.purchase.dto.ResponsePurchase;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.security.redis.RedisService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseServiceImpl {

    private final IPurchaseRepository iPurchaseRepository;
    private final IProductRepository iProductRepository;

    private final IUserHasCouponService iUserHasCouponService;
    private final IUserHasStarbucksCardService iUserHasStarbucksCardService;
    private final IShippingAddressService iShippingAddressService;
    private final KakaoPayService kakaoPayService;
    private final RedisService redisService;


    /**
     * 결제 진입점(분기 : 카카오결제, 스타벅스카드 결제)
     */
    public ResponsePayment startPayment(RequestPurchase requestPurchase, User user) {

        startPaymentLog(requestPurchase);
        if (PurchaseType.isKakaoPay(requestPurchase)) {
            return kakaoApi(requestPurchase, user);
        } else if (PurchaseType.isStarbucksCard(requestPurchase)) {
            return starbucksApi(requestPurchase, user);
        } else {
            throw new ServiceException(NOT_FOUND_PAYMENT_TYPE.getMessage(), NOT_FOUND_PAYMENT_TYPE.getHttpStatus());
        }
    }

    /**
     * 카카오 결제
     */
    @Transactional
    public ResponseKakaoReady kakaoApi(RequestPurchase requestPurchase, User user) {
        String orderId = generateOrderId(requestPurchase);
        checkStock(requestPurchase);
        temporarySaveBill(requestPurchase, user, orderId);
        KakaoPayOrderDto kakaoPayOrderDto = KakaoPayOrderDto.toKakaoOrder(requestPurchase, orderId, user.getId());
        return kakaoPayService.kakaoPayReady(kakaoPayOrderDto);
    }

    /**
     * 스타벅스 결제
     */
    private ResponseStarbucksCardReady starbucksApi(RequestPurchase requestPurchase, User user) {
        String orderId = generateOrderId(requestPurchase);
        checkStock(requestPurchase);
        temporarySaveBill(requestPurchase, user, orderId);

        log.info("아직 지원하지 않는 서비스입니다.");

        return null;
    }

    private Integer getStock(ProductDetail productDetail) {
        return iProductRepository.findById(productDetail.getProductId())
            .orElseThrow(() -> new ServiceException(NOT_FOUND_PRODUCT.getMessage(), NOT_FOUND_PRODUCT.getHttpStatus()))
            .getStock();
    }

    private void checkStock(RequestPurchase requestPurchase) {
        if (requestPurchase.getPurchaseList().stream().anyMatch(p -> getStock(p) < p.getProductQuantity())) {
            throw new ServiceException(OUT_OF_STOCK.getMessage(), OUT_OF_STOCK.getHttpStatus());
        }
    }

    private String generateOrderId(RequestPurchase requestPurchase) {

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String productsNumbers = requestPurchase.getPurchaseList().stream()
            .map(ProductDetail::getProductId)
            .reduce(0L, (a, b) -> a + b)
            .toString()
            .substring(0, 1);
        LongStream longStream = new Random(System.currentTimeMillis()).longs(6, 1, 10);
        String randomNumbers = longStream.mapToObj(String::valueOf)
            .collect(Collectors.joining());
        log.info("[주문 번호 생성] : " + nowDate + productsNumbers + randomNumbers);
        return nowDate + productsNumbers + randomNumbers;
    }

    private void temporarySaveBill(RequestPurchase requestPurchase, User user, String orderId) {
        ResponseShippingAddress shippingAddress = iShippingAddressService.getShippingAddress(
            requestPurchase.getShippingAddressId());
        requestPurchase.getPurchaseList()
            .forEach(
                p -> iPurchaseRepository.save(Purchase.toEntity(p, requestPurchase, shippingAddress, user, orderId)));
    }

    private static void startPaymentLog(RequestPurchase requestPurchase) {
        log.info("[결제 타입] : " + requestPurchase.getPaymentType());
        log.info("[상품 종류 개수] : " + requestPurchase.getPurchaseList().size() + "개");
        log.info("[전체 가격] : " + requestPurchase.getTotalPrice() + "원");
    }

    /**
     * //================================ End Of Payment ================================//
     */
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
        purchase.forEach(purchaseStatus -> purchaseStatus.setProcessStatus(ProcessStatus.PAYMENT_COMPLETE));
    }

    public void endPayment(User loginUser) {
        try {
            String orderId = redisService.getValues("PAYMENT(" + loginUser.getId().toString() + ")").split(",")[0];
            List<Purchase> purchaseByUuid = iPurchaseRepository.findAllByUuid(orderId);
            purchaseByUuid.forEach(Purchase::changeProcessStatus);
            redisService.deleteValues("PAYMENT(" + loginUser.getId().toString() + ")");
            redisService.deleteValues("ORDER_PRODUCTS(" + loginUser.getId().toString() + ")");
        } catch (Exception e) {
            throw new ServiceException("결제 완료 과정 중 서버에서 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void cancelPurchase(User loginUser) {
        try {
            String orderId = redisService.getValues("PAYMENT(" + loginUser.getId().toString() + ")").split(",")[0];
            iPurchaseRepository.deleteAllByUuid(orderId);
            redisService.deleteValues("PAYMENT(" + loginUser.getId().toString() + ")");
            redisService.deleteValues("ORDER_PRODUCTS(" + loginUser.getId().toString() + ")");
        } catch (Exception e) {
            throw new ServiceException("결제 완료 과정 중 서버에서 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
