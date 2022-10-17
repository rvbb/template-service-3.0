package com.rvbb.api.template.validator;

import com.rvbb.api.template.common.constant.ErrorCode;
import com.rvbb.api.template.common.constant.ErrorMessage;
import com.rvbb.api.template.common.constant.SqlOperationName;
import com.rvbb.api.template.common.util.SqlUtils;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.api.template.common.constant.FinanceInfoFieldName;
import com.rvbb.api.template.config.ApplicationConfig;
import com.rvbb.api.template.exception.BizLogicException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class FinanceInfoValidator {

    private final ApplicationConfig applicationConfig;

    public void checkSimilarWithLast(FinanceInfoRes old, FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        double lastExpense = Math.floor(old.getExpense().doubleValue() * 100) / 100;
        double lastPreTaxIncome = Math.floor(old.getPreTaxIncome().doubleValue() * 100) / 100;
        log.info("validate, expense {} == {}, income {}={}", expense, lastExpense, preTaxIncome, lastPreTaxIncome);
        if (old.getCompanyAddress().equalsIgnoreCase(request.getCompanyAddress())
                && old.getCompanyName().equalsIgnoreCase(request.getCompanyName())
                && expense == lastExpense
                && preTaxIncome == lastPreTaxIncome
                && request.getStatus().equals(old.getStatus())) {
            throw new BizLogicException("Your inputted data is similar(not care sensitive case) with last finance information", ErrorCode.NOT_ALLOWED.val);
        }
    }

    public void validateInputType(FinanceInfoInput request) {
        try {
            Double.valueOf(request.getPreTaxIncome());
            Double.valueOf(request.getExpense());
        } catch (NumberFormatException e) {
            throw new BizLogicException("The 'expense' or/and 'preTaxIncome' field must be numeric and length MUST <= 15", HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    public void validateSort(String[] sort) {
        StringBuilder errorMessage = new StringBuilder();
        if (ObjectUtils.isNotEmpty(sort)) {
            for (String oneSort : sort) {
                try {
                    log.info("one sort={}", oneSort);
                    if (StringUtils.isEmpty(oneSort)) {
                        errorMessage.append("The sort require at least one value. ");
                        continue;
                    }
                    String direction = oneSort.substring(0, oneSort.indexOf("("));
                    String field = oneSort.substring(oneSort.indexOf("(") + 1, oneSort.indexOf(")"));
                    if (StringUtils.isEmpty(direction) || StringUtils.isEmpty(field)
                            || ObjectUtils.isEmpty(SqlUtils.getSortValue(direction))
                            || !FinanceInfoFieldName.contains(field)) {
                        errorMessage.append("The sort [" + oneSort + "] " + ErrorMessage.INVALID.getVal());
                    }
                } catch (Exception e) {
                    errorMessage.append("The parameter [" + oneSort + "] parser exception. ");
                    log.debug("Detail condition parser exception: ", e);
                }
            }
            if (errorMessage.length() > 0) {
                errorMessage.append("The 'sort' need to follow format sort=desc(col1)&sort=asc(col3). ");
            }
        }

        log.debug("errorMessage=[{}]", errorMessage.toString());
        if (errorMessage.length() > 0) {
            throw new BizLogicException(ErrorCode.INVALID_INPUT.toString() + ": " + errorMessage.toString(), ErrorCode.INVALID_INPUT.val);
        }
    }

    public void validateFilter(String[] sort, String[] condition, int page, int size) {
        StringBuilder errorMessage = new StringBuilder();
       validateSort(sort);
        if (ObjectUtils.isNotEmpty(condition)) {
            for (String oneCondition : condition) {
                try {
                    if (StringUtils.isEmpty(oneCondition)) {
                        errorMessage.append("condition param require at least one value");
                        continue;
                    }
                    String operator = oneCondition.substring(0, oneCondition.indexOf("("));
                    if (StringUtils.isEmpty(operator) || !SqlOperationName.contains(operator)) {
                        errorMessage.append("Wrong '" + operator + "', the condition is supported only [" + SqlOperationName.asString() + "] operations| ");
                        continue;
                    }
                    String fieldAndVal = oneCondition.substring(oneCondition.indexOf("(") + 1, oneCondition.indexOf(")"));
                    if (StringUtils.isEmpty(fieldAndVal)) {
                        errorMessage.append("The condition parameter [" + oneCondition + "]"+ ErrorMessage.INVALID.getVal());
                        continue;
                    }
                    String[] fieldAndValSeparated = fieldAndVal.split(":");
                    if (ObjectUtils.isEmpty(fieldAndValSeparated) || fieldAndValSeparated.length != 2) {
                        errorMessage.append("The condition parameter [" + oneCondition + "] "+ ErrorMessage.INVALID.getVal());
                        continue;
                    }
                    String field = fieldAndValSeparated[0];
                    String val = fieldAndValSeparated[1];
                    if (StringUtils.isEmpty(field) || StringUtils.isEmpty(val) || !FinanceInfoFieldName.contains(field)) {
                        errorMessage.append("The parameter [" + oneCondition + "] "+ ErrorMessage.INVALID.getVal());
                    }
                } catch (Exception e) {
                    errorMessage.append("The parameter '" + oneCondition + "' parser exception. ");
                    log.debug("Detail condition parser exception: ", e);
                }
            }
            if (errorMessage.length() > 0) {
                errorMessage.append("The 'condition' need to follow format condition=equal(col1,1)&condition=greater(col2,abc). ");
            }
        }
        if (page < 0) {
            errorMessage.append("The 'page' must be positive integer| ");
        }
        if (size < 0) {
            errorMessage.append("The 'size' must be positive integer| ");
        }
        if (size > applicationConfig.getAllowedMaxSize()) {
            errorMessage.append("The 'size' could not allowed over " + applicationConfig.getAllowedMaxSize());
        }
        log.debug("errorMessage=[{}]", errorMessage.toString());
        if (errorMessage.length() > 0) {
            throw new BizLogicException(ErrorCode.INVALID_INPUT.toString() + ": " + errorMessage.toString(), ErrorCode.INVALID_INPUT.val);
        }
    }
}
