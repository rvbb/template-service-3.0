package com.rvbb.food.template.repository.impl;


import com.rvbb.food.template.common.constant.FinanceInfoFieldName;
import com.rvbb.food.template.common.constant.TableName;
import com.rvbb.food.template.common.util.SqlUtils;
import com.rvbb.food.template.config.ApplicationConfig;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.entity.FinanceInfoEntity;
import com.rvbb.food.template.repository.FinanceInfoRepository;
import com.rvbb.food.template.repository.FinanceInfoXpanRepository;
import com.rvbb.food.template.service.mapper.FinanceInfoMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@AllArgsConstructor
@SuppressWarnings(value = "uncheckd")
public class FinanceInfoXpanRepositoryImpl implements FinanceInfoXpanRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final ApplicationConfig applicationConfig;

    private final FinanceInfoRepository financeInfoRepository;

    public Page<FinanceInfoRes> search(FinanceInfoFilterInput filter) {
        StringBuilder search = new StringBuilder("select ");
        search.append(FinanceInfoFieldName.ID).append(",");
        search.append(FinanceInfoFieldName.STATUS).append(",");
        search.append(FinanceInfoFieldName.LAST_UPDATE).append(",");
        search.append(FinanceInfoFieldName.COMPANY_NAME).append(",");
        search.append(FinanceInfoFieldName.COMPANY_ADDRESS).append(",");
        search.append(FinanceInfoFieldName.UUID).append(",");
        search.append(FinanceInfoFieldName.PRE_TAX_INCOME).append(",");
        search.append(FinanceInfoFieldName.EXPENSE);
        search.append(" from ").append(TableName.FINANCE_INFO.toString().toLowerCase());
        StringBuilder count = new StringBuilder("select count(1) from " + TableName.FINANCE_INFO.toString().toLowerCase());

        StringBuilder where = new StringBuilder();
        StringBuilder sort = new StringBuilder();

        if (ObjectUtils.isNotEmpty(filter.getStatus())) {
            where.append(" where ").append(FinanceInfoFieldName.STATUS).append("=").append(filter.getStatus());
        }
        if (StringUtils.isNotEmpty(filter.getCompanyName())) {
            if (where.length() < 1) {
                where.append(" where lower(").append(FinanceInfoFieldName.COMPANY_NAME).append(") like '%").append(filter.getCompanyName().toLowerCase()).append("%'");
            } else {
                where.append(" and lower(").append(FinanceInfoFieldName.COMPANY_NAME).append(") like '%").append(filter.getCompanyName().toLowerCase()).append("%'");
            }
        }
        if (where.length() > 0) {
            search.append(where);
            count.append(where);
        }
        Query countQuery = entityManager.createNativeQuery(count.toString());

        int totalRow = countQuery.getFirstResult();
        int pageSize = ObjectUtils.isEmpty(filter.getPageSize()) ? applicationConfig.getSize() : filter.getPageSize();
        int pageNum = ObjectUtils.isEmpty(filter.getPageNum()) ? applicationConfig.getPage() : filter.getPageNum();

        Map<String, String> sortFields = filter.getSorts();

        if (ObjectUtils.isNotEmpty(sortFields)) {
            buildSort(sortFields, sort);
        } else {
            sort.append(FinanceInfoFieldName.ID).append(" ASC");
        }
        if (sort.length() > 0) {
            search.append(" order by ").append(sort);
        }
        int nextPage = pageNum + 1;
        search.append(" limit ").append(pageSize);
        search.append(" offset ").append(nextPage);
        log.debug("SQL search=[{}]", search);
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
    public Page<FinanceInfoRes> search(String[] sort, String[] condition, int page, int size) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Page<FinanceInfoEntity> searchedList;
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

    private void buildSort(Map<String, String> sortFields, StringBuilder sort) {
        for (Map.Entry<String, String> entry : sortFields.entrySet()) {
            String field = entry.getKey();
            String orderVal = entry.getValue();
            if (sort.length() < 1) {
                sort.append(field).append(" ").append(orderVal);
            } else {
                sort.append(",").append(field).append(" ").append(orderVal);
            }
        }
    }

}
