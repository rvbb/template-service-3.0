package com.rvbb.food.template.service.mapper;


import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.entity.FinanceInfoEntity;
import com.rvbb.food.template.common.util.DateTimeUtil;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FinanceInfoMapper {

    FinanceInfoMapper instance = Mappers.getMapper(FinanceInfoMapper.class);

    @Mapping(source = "lastUpdated", target = "latestUpdate", qualifiedByName = "convertTimeStampToString")
    @Mapping(source = "expense", target = "expense", qualifiedByName = "round")
    @Mapping(source = "preTaxIncome", target = "preTaxIncome", qualifiedByName = "round")
    FinanceInfoRes toDto(FinanceInfoEntity loanJobInformationEntity);

    @Named("convertList")
    List<FinanceInfoRes> convertList(Collection<FinanceInfoEntity> entities);

    FinanceInfoEntity toEntity(FinanceInfoInput request);

    @Named("convertDateToString")
    static String convertDateToString(Date date) {
        return DateTimeUtil.date2string(date, false);
    }

    @Named("convertTimeStampToString")
    static String convertTimeStampToString(Timestamp timestamp) {
        return timestamp.toString();
    }

    @Named("round")
    static BigDecimal roundDouble(BigDecimal val) {
        return BigDecimal.valueOf(Math.floor(val.doubleValue() * 100) / 100);
    }
}

