package com.ghbt.ghbt_starbucks.api.kakaopay.service;

import static com.ghbt.ghbt_starbucks.api.kakaopay.KakaoPayUrl.*;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoApproveResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoCompleteResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoReadyResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.OrderProductDto;
import com.ghbt.ghbt_starbucks.api.product.repository.IProductRepository;
import com.ghbt.ghbt_starbucks.api.shipping_address.dto.ResponseShippingAddress;
import com.ghbt.ghbt_starbucks.api.shipping_address.service.ShippingAddressServiceImpl;
import com.ghbt.ghbt_starbucks.api.user.model.User;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import com.ghbt.ghbt_starbucks.global.security.redis.RedisService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoPayService {

    private final RedisService redisService;
    private final ShippingAddressServiceImpl shippingAddressService;
    private final IProductRepository iProductRepository;
    @Value("${kakaopay.cid}")
    private String cid;
    @Value("${kakaopay.secret}")
    private String adminKey;

    /**
     * 결제 준비
     */
    public KakaoReadyResponse kakaoPayReady(KakaoPayOrderDto kakaoPayOrderDto) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tax_free_amount", "0");
        parameters.add("partner_order_id", kakaoPayOrderDto.getOrderId());
        parameters.add("partner_user_id", kakaoPayOrderDto.getMemberId());
        parameters.add("item_name", kakaoPayOrderDto.getProductName());
        parameters.add("quantity", kakaoPayOrderDto.getProductCount());
        parameters.add("total_amount", kakaoPayOrderDto.getTotalPrice());
        parameters.add("approval_url", APPROVAL.getUrl());
        parameters.add("cancel_url", CANCEL.getUrl());
        parameters.add("fail_url", FAIL.getUrl());

        KakaoReadyResponse kakaoReadyResponse = new RestTemplate().postForObject(
            READY_TO_POST.getUrl(),
            new HttpEntity<>(parameters, getHeaders()),
            KakaoReadyResponse.class
        );

        redisService.deleteValues("PAYMENT(" + kakaoPayOrderDto.getMemberId() + ")");
        redisService.deleteValues("ORDER_PRODUCTS(" + kakaoPayOrderDto.getMemberId() + ")");

        redisService.setValuesWithTimeout(
            "PAYMENT(" + kakaoPayOrderDto.getMemberId() + ")",
            kakaoPayOrderDto.getOrderId() + ","
                + kakaoReadyResponse.getTid() + ","
                + kakaoPayOrderDto.getTotalPrice() + ","
                + kakaoPayOrderDto.getShippingAddressId() + ","
                + kakaoPayOrderDto.getShippingPrice(),
            5 * 60 * 1000
        );

        redisService.setValuesWithTimeout(
            "ORDER_PRODUCTS(" + kakaoPayOrderDto.getMemberId() + ")",
            kakaoPayOrderDto.getProductStringList(),
            5 * 60 * 1000
        );

        log.info("[결제 승인 번호  ]: " + kakaoReadyResponse.getTid());
        log.info("[결제 준비 일시  ]: " + kakaoReadyResponse.getCreated_at());
        log.info("[PC 승인 URL    ]: " + kakaoReadyResponse.getNext_redirect_pc_url());
        log.info("[MOBILE 승인 URL]: " + kakaoReadyResponse.getNext_redirect_mobile_url());

        return kakaoReadyResponse;
    }

    /**
     * 결제 승인
     */
    public KakaoCompleteResponse approveKakaopayment(String pgToken, User loginUser) {
        try {
            String[] orderIdAndTIdAndTotalPrice = redisService.getValues("PAYMENT(" + loginUser.getId().toString() + ")").split(",");
            String orderId = orderIdAndTIdAndTotalPrice[0];
            String tId = orderIdAndTIdAndTotalPrice[1];
            String totalPrice = orderIdAndTIdAndTotalPrice[2];
            String shippingAddressId = orderIdAndTIdAndTotalPrice[3];
            String shippingPrice = orderIdAndTIdAndTotalPrice[4];

            String[] productList = redisService.getValues("ORDER_PRODUCTS(" + loginUser.getId().toString() + ")").split(",");
            List<OrderProductDto> products = Arrays.stream(productList)
                .map(p -> p.split(":"))
                .map(p -> new OrderProductDto(iProductRepository.findById(Long.valueOf(p[0]))
                    .orElseThrow(() -> new ServiceException("주문하신 상품은 판매가 중단되었습니다.", HttpStatus.BAD_REQUEST))
                    , Long.valueOf(p[1])))
                .collect(Collectors.toList());

            // 카카오 요청
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("cid", cid);
            parameters.add("tid", tId);
            parameters.add("partner_order_id", orderId);
            parameters.add("partner_user_id", loginUser.getId().toString());
            parameters.add("pg_token", pgToken);

            KakaoApproveResponse kakaoApproveResponse = new RestTemplate().postForObject(
                APPROVE_TO_POST.getUrl(),
                new HttpEntity<>(parameters, getHeaders()),
                KakaoApproveResponse.class
            );

            ResponseShippingAddress shippingAddress = shippingAddressService.getShippingAddress(Long.valueOf(shippingAddressId));
            if (kakaoApproveResponse.getAmount().getTotal() != Integer.parseInt(totalPrice)) {
                throw new ServiceException("총 금액이 맞지 않습니다. 결제를 취소합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                log.info("[결제 성공]: " + loginUser.getEmail() + "님이 " + totalPrice + " 원이 결제되었습니다.");
            }
            return KakaoCompleteResponse.from(kakaoApproveResponse, products, shippingAddress, Long.valueOf(shippingPrice));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("결제 정보 저장이 제대로 이루어지지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            redisService.deleteValues("PAYMENT(" + loginUser.getId().toString() + ")");
            redisService.deleteValues("ORDER_PRODUCTS(" + loginUser.getId().toString() + ")");
        }
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "KakaoAK " + adminKey;
        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return httpHeaders;
    }
}