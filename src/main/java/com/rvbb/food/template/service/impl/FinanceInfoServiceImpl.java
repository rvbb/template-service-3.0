package com.rvbb.food.template.service.impl;

import com.rvbb.food.template.common.constant.ErrorCode;
import com.rvbb.food.template.common.util.LogIt;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.entity.FinanceInfoEntity;
import com.rvbb.food.template.repository.FinanceInfoRepository;
import com.rvbb.food.template.service.mapper.FinanceInfoMapper;
import com.rvbb.food.template.common.constant.FinanceInfoStatus;
import com.rvbb.food.template.common.util.CommonUtil;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.food.template.exception.BizLogicException;
import com.rvbb.food.template.repository.FinanceInfoXpanRepository;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

@Service
@AllArgsConstructor
public class FinanceInfoServiceImpl implements com.rvbb.food.template.service.FinanceInfoService {

    private final FinanceInfoRepository finInfoRepository;
    private final FinanceInfoXpanRepository financeInfoXpanRepository;

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
