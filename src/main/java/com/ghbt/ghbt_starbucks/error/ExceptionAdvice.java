package com.ghbt.ghbt_starbucks.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity runtimeException(RuntimeException e) {
    return ResponseEntity.internalServerError()
        .body("서버에서 문제가 발생했습니다.");
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity serviceException(ServiceException e) {
    return new ResponseEntity(e.getMessage(), e.getHttpStatus());
  }
}
