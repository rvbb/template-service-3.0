package com.rvbb.food.template.exception;

import org.springframework.http.HttpHeaders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdapterBadRequestException extends RuntimeException {

    private final int status;
    private final HttpHeaders headers;
    private final String body;

    public AdapterBadRequestException(int status, HttpHeaders headers, String body) {
        super("Bad request");
        this.status = status;
        this.headers = headers;
        this.body = body;
    }
}
