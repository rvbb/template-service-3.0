package com.rvbb.food.template.dto.projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * use for Spring Data Projection
 **/
public interface IFinanceInfoProjection {
    long getId();

    BigDecimal getPreTaxIncome();

    String getCompanyName();

    String getCompanyAddress();

    Date getLastUpdate();

    BigDecimal getExpense();

    Byte getStatus();

    String getUuid();
}
