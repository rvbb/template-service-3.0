package com.rvbb.api.template.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorMessage {

    INVALID((String)"is invalid");
    private String val;
}
