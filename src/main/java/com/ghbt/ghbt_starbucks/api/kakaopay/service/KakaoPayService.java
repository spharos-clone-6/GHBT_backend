package com.ghbt.ghbt_starbucks.api.kakaopay.service;

import static com.ghbt.ghbt_starbucks.api.kakaopay.KakoPayUrl.*;

import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoApproveResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoReadyResponse;
import com.ghbt.ghbt_starbucks.api.kakaopay.dto.KakaoPayOrderDto;
import com.ghbt.ghbt_starbucks.global.error.ServiceException;
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

    @Value("${kakaopay.cid}")
    private String cid;
    @Value("${kakaopay.secret}")
    private String admin_Key;
    private KakaoReadyResponse kakaoReady;
    private String orderId;
    private String memberId;

    /**
     * 결제 준비
     */
    public KakaoReadyResponse kakaoPayReady(KakaoPayOrderDto kakaoPayOrderDto) {
        orderId = kakaoPayOrderDto.getOrderId();
        memberId = kakaoPayOrderDto.getMemberId();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tax_free_amount", "0");
        parameters.add("partner_order_id", orderId);
        parameters.add("partner_user_id", memberId);
        parameters.add("item_name", kakaoPayOrderDto.getProductName());
        parameters.add("quantity", kakaoPayOrderDto.getProductCount());
        parameters.add("total_amount", kakaoPayOrderDto.getTotalPrice());
        parameters.add("approval_url", APPROVAL.getUrl());
        parameters.add("cancel_url", CANCEL.getUrl());
        parameters.add("fail_url", FAIL.getUrl());

        //결제 준비를 요청한다(카카오에서 주문 아이디를 줌.)
        return postRestTemplateToReady(parameters);
    }

    private KakaoReadyResponse postRestTemplateToReady(MultiValueMap<String, String> parameters) {
        KakaoReadyResponse kakaoReadyResponse = new RestTemplate().postForObject(
            READY_TO_POST.getUrl(),
            new HttpEntity<>(parameters, getHeaders()),
            KakaoReadyResponse.class
        );
        log.info("[ 결제 승인 번호     ]: " + kakaoReadyResponse.getTid());
        log.info("[ 결제 준비 일시     ]: " + kakaoReadyResponse.getCreated_at());
        log.info("[ PC 승인 URL      ]: " + kakaoReadyResponse.getNext_redirect_pc_url());
        log.info("[ MOBILE 승인 URL  ]: " + kakaoReadyResponse.getNext_redirect_mobile_url());

        kakaoReady = kakaoReadyResponse;
        return kakaoReadyResponse;
    }

    /**
     * 결제 승인
     */
    public KakaoApproveResponse approveResponse(String pgToken) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", orderId);
        parameters.add("partner_user_id", memberId);
        parameters.add("pg_token", pgToken);

        if (orderId.equals("1")) {
            throw new ServiceException("결제 내역이 올바르지 않습니다.", HttpStatus.NOT_FOUND);
        }

        //==나의 주문내역과 카카오에서 결제할 정보를 비교해서 이상 없으면 진행==///
        //카카오로 결제 승인을 보낸다
        return postRestTemplateToApprove(parameters);
    }


    private KakaoApproveResponse postRestTemplateToApprove(MultiValueMap<String, String> parameters) {
        return new RestTemplate().postForObject(
            APPROVE_TO_POST.getUrl(),
            new HttpEntity<>(parameters, getHeaders()),
            KakaoApproveResponse.class
        );

    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;
        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }
}