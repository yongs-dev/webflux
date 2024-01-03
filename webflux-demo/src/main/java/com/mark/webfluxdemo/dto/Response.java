package com.mark.webfluxdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
public class Response {

    private int output;
    private Date date = new Date();

    public Response(int output) {
        this.output = output;
    }
}
