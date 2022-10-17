package com.rvbb.api.template.repository;


import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoFilterInput;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface IFinanceInfoXpanRepository {

    boolean updateByStatus(FinanceInfoInput request, Short status);
    Page<FinanceInfoRes> search(FinanceInfoFilterInput filter);
    Page<FinanceInfoRes> search(String[] sort, String[] condition, int page, int size);

}
