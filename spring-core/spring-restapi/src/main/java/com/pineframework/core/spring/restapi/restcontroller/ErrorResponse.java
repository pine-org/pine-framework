package com.pineframework.core.spring.restapi.restcontroller;

import com.pineframework.core.business.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private final String message;

    private final int code;

    private final HttpStatus httpStatus;

    public ErrorResponse(String message, int code, HttpStatus httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public static ErrorResponse from(AbstractException e) {
        return new ErrorResponse(e.getMessage(), e.getCode(), HttpStatus.resolve(e.getCode()));
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
