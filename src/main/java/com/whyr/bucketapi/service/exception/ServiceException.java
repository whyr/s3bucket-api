package com.whyr.bucketapi.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public ServiceException(String message) {

        this(message, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ServiceException(String message, HttpStatus httpStatus) {

        super(message);

        this.message = message;
        this.httpStatus = httpStatus;

    }

}
