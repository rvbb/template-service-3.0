package com.rvbb.api.template.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizLogicException extends RuntimeException {
    private final int code;

    public BizLogicException(String message, int code) {
        super(message);
        this.code = code;
    }
}
