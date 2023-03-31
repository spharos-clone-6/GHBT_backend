package com.ghbt.ghbt_starbucks.api.user.model;

import com.ghbt.ghbt_starbucks.global.error.ServiceException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AdAgreement {
    NOT_AGREEMENT("none", "수신 미동의"),
    AGREEMENT_TO_EMAIL("email", "EMAIL 수신동의"),
    AGREEMENT_TO_SMS("sms", "SMS 수신동의"),
    AGREEMENT_TO_ALL("all", "전체 수신동의"),

    ;
    private String key;
    private String message;

    public static AdAgreement toEnumByKey(String key) {
        return Arrays.stream(AdAgreement.values())
            .filter(ad -> ad.getKey().equals(key))
            .findFirst()
            .orElseThrow(() -> new ServiceException("수신 동의를 저장 하는 과정에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
