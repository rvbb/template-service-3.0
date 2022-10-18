package com.rvbb.food.template.service.mapper;

import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.entity.FinanceInfoEntity;
import com.rvbb.food.template.entity.FinanceInfoEntity.FinanceInfoEntityBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-18T21:28:21+0700",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 18.0.2 (Amazon.com Inc.)"
)
public class FinanceInfoMapperImpl implements FinanceInfoMapper {

    @Override
    public FinanceInfoRes toDto(FinanceInfoEntity loanJobInformationEntity) {
        if ( loanJobInformationEntity == null ) {
            return null;
        }

        FinanceInfoRes financeInfoRes = new FinanceInfoRes();

        financeInfoRes.setLatestUpdate( FinanceInfoMapper.convertTimeStampToString( loanJobInformationEntity.getLastUpdated() ) );
        financeInfoRes.setExpense( FinanceInfoMapper.roundDouble( loanJobInformationEntity.getExpense() ) );
        financeInfoRes.setPreTaxIncome( FinanceInfoMapper.roundDouble( loanJobInformationEntity.getPreTaxIncome() ) );
        financeInfoRes.setId( loanJobInformationEntity.getId() );
        financeInfoRes.setCompanyName( loanJobInformationEntity.getCompanyName() );
        financeInfoRes.setCompanyAddress( loanJobInformationEntity.getCompanyAddress() );
        financeInfoRes.setStatus( loanJobInformationEntity.getStatus() );
        financeInfoRes.setUuid( loanJobInformationEntity.getUuid() );

        return financeInfoRes;
    }

    @Override
    public List<FinanceInfoRes> convertList(Collection<FinanceInfoEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<FinanceInfoRes> list = new ArrayList<FinanceInfoRes>( entities.size() );
        for ( FinanceInfoEntity financeInfoEntity : entities ) {
            list.add( toDto( financeInfoEntity ) );
        }

        return list;
    }

    @Override
    public FinanceInfoEntity toEntity(FinanceInfoInput request) {
        if ( request == null ) {
            return null;
        }

        FinanceInfoEntityBuilder financeInfoEntity = FinanceInfoEntity.builder();

        if ( request.getPreTaxIncome() != null ) {
            financeInfoEntity.preTaxIncome( new BigDecimal( request.getPreTaxIncome() ) );
        }
        if ( request.getExpense() != null ) {
            financeInfoEntity.expense( new BigDecimal( request.getExpense() ) );
        }
        financeInfoEntity.companyName( request.getCompanyName() );
        financeInfoEntity.companyAddress( request.getCompanyAddress() );
        financeInfoEntity.status( request.getStatus() );

        return financeInfoEntity.build();
    }
}
