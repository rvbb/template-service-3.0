package com.rvbb.food.template.common.util;

import com.rvbb.food.template.common.constant.FinanceInfoFieldName;
import com.rvbb.food.template.common.constant.SqlOperationName;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.entity.FinanceInfoEntity;
import com.rvbb.food.template.repository.spec.SpecCustom;
import com.rvbb.food.template.service.mapper.FinanceInfoMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlUtils {

    public static List<FinanceInfoEntity> fromResultList(List<Object[]> objectList) {
        List<FinanceInfoEntity> converted = new ArrayList<>();
        objectList.forEach(entityAsObjectArray -> {
            log.info("FinanceInfoEntity as Object array=[{}]", entityAsObjectArray);
            FinanceInfoEntity entity = FinanceInfoEntity.builder()
                    .id(Long.parseLong(String.valueOf(entityAsObjectArray[0])))
                    .status(Byte.valueOf(String.valueOf(entityAsObjectArray[1])))
                    .companyName(String.valueOf(entityAsObjectArray[3]))
                    .companyAddress(String.valueOf(entityAsObjectArray[4]))
                    .uuid(String.valueOf(entityAsObjectArray[5]))
                    .preTaxIncome(BigDecimal.valueOf(Double.parseDouble(String.valueOf(entityAsObjectArray[6]))))
                    .expense(BigDecimal.valueOf(Double.parseDouble(String.valueOf(entityAsObjectArray[7]))))
                    .build();
            entity.setLastUpdated(Timestamp.valueOf(String.valueOf(entityAsObjectArray[2])));
            converted.add(entity);
        });

        return converted;
    }

    public static Sort.Direction getSortValue(String direction) {
        return direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    /**
     * sort=desc(col1)&sort=asc(col3)
     * need to validate before in Controller
     **/
    public static Sort buildSort(String[] sort) {
        Sort result = null;
        if (ObjectUtils.isNotEmpty(sort)) {
            for (String oneSort : sort) {
                String direction = oneSort.substring(0, oneSort.indexOf("("));
                String field = oneSort.substring(oneSort.indexOf("(") + 1, oneSort.indexOf(")"));
                if (ObjectUtils.isNotEmpty(getSortValue(direction))) {
                    result = Sort.by(getSortValue(direction), field);
                }
            }
        }
        log.debug("Sort=[{}]", result);
        return result;
    }

    /**
     * condition=condition=equal(col1:1)&condition=greater(col2:abc)
     * need to validate before in Controller
     **/
    public static <T> Specification<T> buildSpec(String[] condition, Class<T> entityClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ObjectUtils.isEmpty(condition)) {
            return null;
        }
        Specification<T> result = null;
        T clazz = entityClass.getDeclaredConstructor().newInstance();
        for (String oneCondition : condition) {
            String operator = oneCondition.substring(0, oneCondition.indexOf("("));
            String[] fieldAndVal = oneCondition.substring(oneCondition.indexOf("(") + 1, oneCondition.indexOf(")")).split(":");
            String field = fieldAndVal[0];
            String val = fieldAndVal[1];
            String fieldPojo = "";
            if (clazz instanceof FinanceInfoEntity) {
                fieldPojo = FinanceInfoFieldName.getAttrByName(field);
            } else {
                //add more compare entities
                log.debug("There is not implementation of {} object", clazz);
            }
            switch (SqlOperationName.valueOf(operator.toUpperCase())) {
                case LIKE:
                    if (result != null) {
                        result = result.and(SpecCustom.similar(fieldPojo, val));
                    } else {
                        result = Specification.where(SpecCustom.similar(fieldPojo, val));
                    }
                    break;
                case GREATEROREQUALS:
                    break;
                default: //case EQUALS:
                    if (result != null) {
                        result = result.and(SpecCustom.equals(fieldPojo, val));
                    } else {
                        result = Specification.where(SpecCustom.equals(fieldPojo, val));
                    }
                    break;
            }
        }
        log.debug("Specification=[{}]", result);
        return result;
    }

    public static Page<FinanceInfoRes> convertPage(Page<FinanceInfoEntity> page) {
        if (ObjectUtils.isEmpty(page)) {
            log.debug("Page<FinanceInfoEntity> is null");
            return null;
        }
        List<FinanceInfoRes> entities = FinanceInfoMapper.instance.convertList(page.getContent());
        return new PageImpl<>(entities, page.getPageable(), page.getTotalElements());
    }
}
