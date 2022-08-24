package com.whyr.bucketapi.controller.exception;

import com.whyr.bucketapi.model.exception.ApiExceptionDto;
import com.whyr.bucketapi.service.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    private ResponseEntity<ApiExceptionDto> returnException(ServiceException ex) {

        ApiExceptionDto apiExceptionDto = new ApiExceptionDto();
        apiExceptionDto.setStatusCode(ex.getHttpStatus().value());
        apiExceptionDto.setStatusMessage(ex.getHttpStatus().getReasonPhrase());
        apiExceptionDto.setErrorMessage(ex.getMessage());

        return new ResponseEntity<>(apiExceptionDto, ex.getHttpStatus());

    }

}
