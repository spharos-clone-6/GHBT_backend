package com.ghbt.ghbt_starbucks.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

  private HttpStatus httpStatus;
  private String message;

  public ServiceException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
