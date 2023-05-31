package com.rvbb.food.template.validator;

import com.rvbb.food.template.common.constant.ErrorCode;
import com.rvbb.food.template.common.constant.ErrorMessage;
import com.rvbb.food.template.common.constant.SqlOperationName;
import com.rvbb.food.template.common.util.SqlUtils;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.common.constant.FinanceInfoFieldName;
import com.rvbb.food.template.config.ApplicationConfig;
import com.rvbb.food.template.exception.BizLogicException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class FinanceInfoValidator {

    private final ApplicationConfig applicationConfig;

    public void checkSimilarWithLast(FinanceInfoRes old, FinanceInfoInput request) {
        double expense = Math.floor(Double.parseDouble(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.parseDouble(request.getPreTaxIncome()) * 100) / 100;
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
            throw new BizLogicException("The 'expense' or/and 'preTaxIncome' field must be numeric and length MUST <= 15", HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validateSort(String[] sort) {
        StringBuilder errorMessage = new StringBuilder();
        if (ObjectUtils.isNotEmpty(sort)) {
            validateSortFields(sort, errorMessage);
            if (errorMessage.length() > 0) {
                errorMessage.append("The 'sort' need to follow format sort=desc(col1)&sort=asc(col3). ");
            }
        }

        log.debug("errorMessage=[{}]", errorMessage);
        if (errorMessage.length() > 0) {
            throw new BizLogicException(ErrorCode.INVALID_INPUT + ": " + errorMessage, ErrorCode.INVALID_INPUT.val);
        }
    }

    public void validateFilter(String[] sort, String[] condition, int page, int size) {
        StringBuilder errorMessage = new StringBuilder();
       validateSort(sort);
        if (ObjectUtils.isNotEmpty(condition)) {
            validateConditions(condition, errorMessage);
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
            errorMessage.append("The 'size' could not allowed over ").append(applicationConfig.getAllowedMaxSize());
        }
        if (errorMessage.length() > 0) {
            throw new BizLogicException(ErrorCode.INVALID_INPUT + ": " + errorMessage, ErrorCode.INVALID_INPUT.val);
        }
    }

    private void validateSortFields(String[] sort, StringBuilder errorMessage){
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
                    errorMessage.append("The sort [").append(oneSort).append("] ").append(ErrorMessage.INVALID.getVal());
                }
            } catch (Exception e) {
                errorMessage.append("The parameter [").append(oneSort).append("] parser exception. ");
                log.debug("Detail condition parser exception: ", e);
            }
        }
    }

    private void validateConditions(String[] condition, StringBuilder errorMessage){
        for (String oneCondition : condition) {
            try {
                validEmptyCondition(oneCondition, errorMessage);
                String operator = oneCondition.substring(0, oneCondition.indexOf("("));
                if (StringUtils.isEmpty(operator) || !SqlOperationName.contains(operator)) {
                    errorMessage.append("Wrong '").append(operator).append("', the condition is supported only [").append(SqlOperationName.asString()).append("] operations| ");
                }
                String fieldAndVal = oneCondition.substring(oneCondition.indexOf("(") + 1, oneCondition.indexOf(")"));
                if (StringUtils.isEmpty(fieldAndVal)) {
                    errorMessage.append("The condition parameter [").append(oneCondition).append("]").append(ErrorMessage.INVALID.getVal());
                }
                String[] fieldAndValSeparated = fieldAndVal.split(":");
                if (ObjectUtils.isEmpty(fieldAndValSeparated) || fieldAndValSeparated.length != 2) {
                    errorMessage.append("The condition parameter [").append(oneCondition).append("] ").append(ErrorMessage.INVALID.getVal());
                }
                String field = fieldAndValSeparated[0];
                String val = fieldAndValSeparated[1];
                if (StringUtils.isEmpty(field) || StringUtils.isEmpty(val) || !FinanceInfoFieldName.contains(field)) {
                    errorMessage.append("The parameter [").append(oneCondition).append("] ").append(ErrorMessage.INVALID.getVal());
                }
            } catch (Exception e) {
                errorMessage.append("The parameter '").append(oneCondition).append("' parser exception. ");
                log.debug("Detail condition parser exception: ", e);
            }
        }
    }

    private void validEmptyCondition(String oneCondition, StringBuilder errorMessage){
        if (StringUtils.isEmpty(oneCondition)) {
            errorMessage.append("condition param require at least one value");
        }
    }
}
