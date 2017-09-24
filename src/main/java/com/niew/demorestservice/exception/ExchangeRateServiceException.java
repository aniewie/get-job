package com.niew.demorestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class ExchangeRateServiceException extends RuntimeException {
    public ExchangeRateServiceException(String message) {
        super(message);
    }
}
