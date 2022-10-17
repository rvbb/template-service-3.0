package com.rvbb.api.template.common.constant;

import lombok.Getter;

@Getter
public enum FinanceInfoFieldName {
    STATUS("status"),
    ID("id"),
    COMPANY_NAME("companyName"),
    COMPANY_ADDRESS("companyAddress"),
    UUID("uuid"),
    LAST_UPDATE("lastUpdate"),
    PRE_TAX_INCOME("preTaxIncome"),
    EXPENSE("expense");

    private String attribute;
    FinanceInfoFieldName(String attribute) {
        this.attribute = attribute;
    }

    public static String asString() {
        return STATUS.toString()
                + ", " + LAST_UPDATE.toString()
                + ", " + COMPANY_NAME.toString();
    }

    public static boolean contains(String enumAsString) {
        for (FinanceInfoFieldName item : FinanceInfoFieldName.values()) {
            if (item.toString().equalsIgnoreCase(enumAsString)) {
                return true;
            }
        }
        return false;
    }

    public static String getAttrByName(String name) {
        for (FinanceInfoFieldName item : FinanceInfoFieldName.values()) {
            if (item.toString().equalsIgnoreCase(name)) {
                return item.getAttribute();
            }
        }
        return "";
    }
}
