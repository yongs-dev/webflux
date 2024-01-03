package com.mark.webfluxdemo.exceptionhandler;

import com.mark.webfluxdemo.dto.InputFailedValidationResponse;
import com.mark.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException ex) {
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setErrorCode(InputValidationException.getErrorCode());
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            return ResponseEntity.badRequest().body(response);
    }
}
