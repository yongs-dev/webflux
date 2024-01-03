package com.mark.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class InputFailedValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
