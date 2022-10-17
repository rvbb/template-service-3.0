package com.rvbb.api.template.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceInfoStatus {
    XX((byte)1),
    YY((byte)2),
    ZZ((byte)3);
    private Byte value;
}
