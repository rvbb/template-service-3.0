package com.rvbb.food.template.service;

import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoFilterInput;
import org.springframework.data.domain.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FinanceInfoService {

    List<FinanceInfoRes> list();

    FinanceInfoRes update(String uuid, FinanceInfoInput request);

    FinanceInfoRes create(FinanceInfoInput request);

    FinanceInfoRes get(String uuid);

    FinanceInfoRes del(String uuid);

    FinanceInfoRes getLast();

    Page<FinanceInfoRes> doFilter(FinanceInfoFilterInput filter);

    Page<FinanceInfoRes> doFilter(String[] sort, String[] condition, int page, int size) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;
}
