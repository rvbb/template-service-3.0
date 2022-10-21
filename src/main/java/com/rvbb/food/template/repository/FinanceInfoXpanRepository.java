package com.rvbb.food.template.repository;


import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoFilterInput;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceInfoXpanRepository {

    boolean updateByStatus(FinanceInfoInput request, Short status);
    Page<FinanceInfoRes> search(FinanceInfoFilterInput filter);
    Page<FinanceInfoRes> search(String[] sort, String[] condition, int page, int size);

}
