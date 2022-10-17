package com.rvbb.api.template.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
public class AdapterBadRequestException extends HystrixBadRequestException {

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
