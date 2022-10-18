package com.rvbb.food.template.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {
    private static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static String formatDate(Date date) {
        if (date != null) {
            DateFormat format = new SimpleDateFormat(FORMAT_DATE);
            return format.format(date);
        }
        return "";
    }

    public static String getFormatTimestamp(Timestamp date) {
        if (date != null) {
            DateFormat format = new SimpleDateFormat(FORMAT_TIMESTAMP);
            return format.format(date);
        }
        return "";
    }

    public static String date2string(Date date, boolean onlyDate) {
        if (date != null) {
            DateFormat format = null;
            if (onlyDate) {
                format = new SimpleDateFormat(FORMAT_DATE);
            } else {
                format = new SimpleDateFormat(FORMAT_TIMESTAMP);
            }
            return format.format(date);
        }
        return "";
    }

    public static java.util.Date string2Date(String dateString, boolean onlyDate) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        String format = FORMAT_DATE;
        if (!onlyDate) {
            format = FORMAT_TIMESTAMP;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            log.debug("convert string to date exception", e);
        }
        return null;
    }

    public static LocalDateTime getLocalDateTime(String timestampString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TIMESTAMP);
        return LocalDateTime.from(formatter.parse(timestampString));
    }

}
