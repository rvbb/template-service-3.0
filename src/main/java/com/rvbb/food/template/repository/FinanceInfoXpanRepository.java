package com.rvbb.food.template.repository;

import com.rvbb.food.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;

@Repository
public interface FinanceInfoXpanRepository {
    Page<FinanceInfoRes> search(FinanceInfoFilterInput filter);

    Page<FinanceInfoRes> search(String[] sort, String[] condition, int page, int size) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
