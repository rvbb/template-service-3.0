package com.rvbb.api.template.common.constant;

public enum ErrorCode {
    NOT_ALLOWED(5001),
    EMPTY_RESULT(5002),
    INVALID_INPUT(5004);

    public final Integer val;

    ErrorCode(Integer val) {
        this.val = val;
    }
}
