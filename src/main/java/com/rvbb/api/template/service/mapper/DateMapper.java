package com.rvbb.api.template.service.mapper;

import com.rvbb.api.template.common.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.sql.Date;
import java.sql.Timestamp;

@Mapper
public interface DateMapper {


    @Named("mapDateToString")
    static String mapDateToString(Date date) {
        return DateTimeUtil.formatDate(date);
    }

    @Named("mapTimestampToString")
    static String mapTimestampToString(Timestamp date) {
        return DateTimeUtil.getFormatTimestamp(date);
    }
}
