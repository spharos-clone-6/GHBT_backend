package com.ghbt.ghbt_starbucks.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

//  @ExceptionHandler(RuntimeException.class)
//  public ResponseEntity runtimeException(RuntimeException e) {
//    return ResponseEntity.internalServerError()
//        .body("서버에서 문제가 발생했습니다.");
//  }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceException(ServiceException e) {
        log.error("[ Service Exception ]" + " " + e.getHttpStatus().toString() + " : " + e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
