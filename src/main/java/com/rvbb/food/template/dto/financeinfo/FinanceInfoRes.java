package com.rvbb.food.template.dto.financeinfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinanceInfoRes {
    private long id;

    private BigDecimal preTaxIncome;

    private String companyName;

    private String companyAddress;

    private String latestUpdate;

    private BigDecimal expense;

    private Byte status;

    private String uuid;
}
