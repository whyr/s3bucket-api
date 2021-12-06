package com.triggle.bucketapi.controller.exception;

import com.triggle.bucketapi.model.exception.ApiExceptionDto;
import com.triggle.bucketapi.service.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    private ResponseEntity<ApiExceptionDto> returnException(ServiceException ex) {

        ApiExceptionDto apiExceptionDto = new ApiExceptionDto();
        apiExceptionDto.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiExceptionDto, ex.getHttpStatus());

    }

}
