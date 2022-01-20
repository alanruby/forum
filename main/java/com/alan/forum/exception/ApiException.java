package com.alan.forum.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ApiException {
  private final String message;
  private final Throwable throwable;
  private final HttpStatus httpStatus;
  private final ZonedDateTime zonedDateTime;
  
  public ApiException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
    super();
    this.message = message;
    this.throwable = throwable;
    this.httpStatus = httpStatus;
    this.zonedDateTime = zonedDateTime;
  }

  public String getMessage() {
    return message;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public ZonedDateTime getZonedDateTime() {
    return zonedDateTime;
  }

  @Override
  public String toString() {
    return "ApiException [message=" + message + ", throwable=" + throwable + ", httpStatus=" + httpStatus
        + ", zonedDateTime=" + zonedDateTime + "]";
  }
  
  
}