package com.rvbb.api.template.repository.impl;


import com.rvbb.api.template.common.constant.FinanceInfoFieldName;
import com.rvbb.api.template.common.constant.TableName;
import com.rvbb.api.template.common.util.SqlUtils;
import com.rvbb.api.template.config.ApplicationConfig;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.api.template.entity.FinanceInfoEntity;
import com.rvbb.api.template.repository.IFinanceInfoRepository;
import com.rvbb.api.template.repository.IFinanceInfoXpanRepository;
import com.rvbb.api.template.service.mapper.FinanceInfoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
public class FinanceInfoXpanRepositoryImpl implements IFinanceInfoXpanRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final ApplicationConfig applicationConfig;

    private final IFinanceInfoRepository financeInfoRepository;

    @Transactional
    @Override
    public boolean updateByStatus(FinanceInfoInput request, Short status) {
        String updateQuery = "update " + TableName.FINANCE_INFO.toString().toLowerCase() + " l " +
                "set l.company_address = ?1, " +
                "l.company_name = ?2," +
                "l.pre_tax_income = ?3, " +
                "l.expense = ?4, " +
                "l.last_update = ?5 " +
                "where l.status = ?6";
        Query query = entityManager.createNativeQuery(updateQuery);
        query.setParameter(1, request.getCompanyAddress());
        query.setParameter(2, request.getCompanyName());
        query.setParameter(3, request.getPreTaxIncome());
        query.setParameter(4, request.getExpense());
        query.setParameter(5, new Date());
        query.setParameter(6, status);
        entityManager.flush();
        Integer countUpdated = query.executeUpdate();
        entityManager.clear();
        return countUpdated > 0;
    }

    public Page<FinanceInfoRes> search(FinanceInfoFilterInput filter) {
        StringBuilder search = new StringBuilder("select ");
        search.append(FinanceInfoFieldName.ID.toString() + ",");
        search.append(FinanceInfoFieldName.STATUS.toString() + ",");
        search.append(FinanceInfoFieldName.LAST_UPDATE.toString() + ",");
        search.append(FinanceInfoFieldName.COMPANY_NAME.toString() + ",");
        search.append(FinanceInfoFieldName.COMPANY_ADDRESS.toString() + ",");
        search.append(FinanceInfoFieldName.UUID.toString() + ",");
        search.append(FinanceInfoFieldName.PRE_TAX_INCOME.toString() + ",");
        search.append(FinanceInfoFieldName.EXPENSE.toString() + "");

        search.append(" from " + TableName.FINANCE_INFO.toString().toLowerCase());

        StringBuilder count = new StringBuilder("select count(1) from " + TableName.FINANCE_INFO.toString().toLowerCase());

        StringBuilder where = new StringBuilder();
        StringBuilder sort = new StringBuilder();

        if (ObjectUtils.isNotEmpty(filter.getStatus())) {
            where.append(" where " + FinanceInfoFieldName.STATUS.toString() + "=" + filter.getStatus());
        }
        if (StringUtils.isNotEmpty(filter.getCompanyName())) {
            if (where.length() < 1) {
                where.append(" where lower(" + FinanceInfoFieldName.COMPANY_NAME.toString() + ") like '%" + filter.getCompanyName().toLowerCase() + "%'");
            } else {
                where.append(" and lower(" + FinanceInfoFieldName.COMPANY_NAME.toString() + ") like '%" + filter.getCompanyName().toLowerCase() + "%'");
            }
        }
        if (where.length() > 0) {
            search.append(where.toString());
            count.append(where);
        }
        Query countQuery = entityManager.createNativeQuery(count.toString());

        int totalRow = countQuery.getFirstResult();
        int pageSize = ObjectUtils.isEmpty(filter.getPageSize()) ? applicationConfig.getSize() : filter.getPageSize();
        int pageNum = ObjectUtils.isEmpty(filter.getPageNum()) ? applicationConfig.getPage() : filter.getPageNum();

        Map<String, String> sortFields = filter.getSorts();

        if (ObjectUtils.isNotEmpty(sortFields)) {
            for (Map.Entry<String, String> entry : sortFields.entrySet()) {
                String field = entry.getKey();
                String orderVal = entry.getValue();
                if (sort.length() < 1) {
                    sort.append(field + " " + orderVal);
                } else {
                    sort.append("," + field + " " + orderVal);
                }
            }
        } else {
            sort.append(FinanceInfoFieldName.ID.toString() + " ASC");
        }
        if (sort.length() > 0) {
            search.append(" order by " + sort);
        }
        int nextPage = pageNum + 1;
        search.append(" limit " + pageSize);
        search.append(" offset " + nextPage);
        log.debug("SQL search=[{}]", search.toString());
        Query querySearch = entityManager.createNativeQuery(search.toString());

        List<Object[]> resultList = querySearch.getResultList();
        List<FinanceInfoEntity> entityList = SqlUtils.fromResultList(resultList);
        List<FinanceInfoRes> resList = FinanceInfoMapper.instance.convertList(entityList);

        if (resList.size() != totalRow) {
            log.debug("Wrong in pagination - total record in db does not equals fetched records");
        }
        Pageable pageable = PageRequest.of(nextPage, resList.size());
        return new PageImpl<>(resList, pageable, totalRow);
    }

    @Override
    public Page<FinanceInfoRes> search(String[] sort, String[] condition, int page, int size) {
        Page<FinanceInfoEntity> searchedList = null;
        Specification<FinanceInfoEntity> spec = SqlUtils.buildSpec(condition, FinanceInfoEntity.class);
        Sort order = SqlUtils.buildSort(sort);
        Pageable pageable = PageRequest.of(page + 1, size);
        if (ObjectUtils.isEmpty(order)) {
            pageable = PageRequest.of(page + 1, size, order);
        }
        if (ObjectUtils.isNotEmpty(spec)) {
            searchedList = financeInfoRepository.findAll(spec, pageable);
        } else {
            searchedList = financeInfoRepository.findAll(pageable);
        }
        return SqlUtils.convertPage(searchedList);
    }

}