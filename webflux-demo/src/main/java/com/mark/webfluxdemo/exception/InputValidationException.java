package com.mark.webfluxdemo.exception;

import lombok.Getter;

public class InputValidationException extends RuntimeException {

    private static final String MSG = "allowed range is 10 - 20";

    @Getter
    private static final int errorCode = 100;

    @Getter
    private final int input;

    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
    }
}
