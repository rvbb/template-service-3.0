package com.rvbb.api.template.service.impl;

import com.rvbb.api.template.common.constant.ErrorCode;
import com.rvbb.api.template.common.util.LogIt;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.api.template.entity.FinanceInfoEntity;
import com.rvbb.api.template.repository.IFinanceInfoRepository;
import com.rvbb.api.template.service.mapper.FinanceInfoMapper;
import com.rvbb.api.template.common.constant.FinanceInfoStatus;
import com.rvbb.api.template.common.util.CommonUtil;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.api.template.exception.BizLogicException;
import com.rvbb.api.template.repository.IFinanceInfoXpanRepository;
import com.rvbb.api.template.service.IFinanceInfoService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

@Service
@AllArgsConstructor
public class FinanceInfoService implements IFinanceInfoService {

    private final IFinanceInfoRepository finInfoRepository;
    private final IFinanceInfoXpanRepository financeInfoXpanRepository;

    @Override
    @LogIt
    public FinanceInfoRes getLast() {
        FinanceInfoEntity entity = finInfoRepository.getLast();
        if (ObjectUtils.isEmpty(entity)) {
            throw new EntityNotFoundException("Not found last financial information");
        }
        return FinanceInfoMapper.instance.toDto(entity);
    }

    @Override
    @LogIt
    public FinanceInfoRes get(String uuid) {
        FinanceInfoEntity financeInfo = getByUuid(uuid);
        return FinanceInfoMapper.instance.toDto(financeInfo);
    }

    @Override
    @LogIt
    public FinanceInfoRes create(FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        FinanceInfoEntity newEntity = FinanceInfoEntity.builder()
                .preTaxIncome(BigDecimal.valueOf(preTaxIncome))
                .companyAddress(request.getCompanyAddress())
                .companyName(request.getCompanyName())
                .expense(BigDecimal.valueOf(expense))
                .status(FinanceInfoStatus.XX.getValue())
                .uuid(CommonUtil.unique())
                .build();
        newEntity.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        finInfoRepository.save(newEntity);
        return FinanceInfoMapper.instance.toDto(newEntity);
    }

    @Override
    @LogIt
    public FinanceInfoRes update(String uuid, FinanceInfoInput request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome = Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        FinanceInfoEntity financeInfo = getByUuid(uuid);
        financeInfo.setPreTaxIncome(BigDecimal.valueOf(preTaxIncome));
        financeInfo.setCompanyAddress(request.getCompanyAddress());
        financeInfo.setCompanyName(request.getCompanyName());
        financeInfo.setExpense(BigDecimal.valueOf(expense));
        financeInfo.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        financeInfo.setStatus(request.getStatus());
        return FinanceInfoMapper.instance.toDto(finInfoRepository.save(financeInfo));
    }

    @Override
    @LogIt
    public List<FinanceInfoRes> list() {
        Collection<FinanceInfoEntity> collection = finInfoRepository.findAll();
        Collection<FinanceInfoRes> response = FinanceInfoMapper.instance.convertList(collection);
        return response.stream().collect(toCollection(ArrayList::new));
    }

    @Override
    @LogIt
    public FinanceInfoRes del(String uuid) {
        FinanceInfoEntity oldEntity = null;
        try {
            oldEntity = getByUuid(uuid);
        }catch (EntityNotFoundException e){
            throw new BizLogicException("The finanace information is not existence", ErrorCode.EMPTY_RESULT.val);
        }
        finInfoRepository.delete(oldEntity);
        return FinanceInfoMapper.instance.toDto(oldEntity);
    }

    private FinanceInfoEntity getByUuid(String uuid){
        Optional<FinanceInfoEntity> optional = finInfoRepository.findByUuid(uuid);
        return optional.orElseThrow(
                () -> new EntityNotFoundException("Not found finance information with uuid : " + uuid));
    }

    @Override
    public Page<FinanceInfoRes> doFilter(FinanceInfoFilterInput filter) {
        return financeInfoXpanRepository.search(filter);
    }

    @Override
    public Page<FinanceInfoRes> doFilter(String[] sort, String[] condition, int page, int size) {
        return financeInfoXpanRepository.search(sort, condition, page, size);
    }

}