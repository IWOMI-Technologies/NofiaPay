package com.iwomi.nofiaPay.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CoreUtils {

    public static Date localDateToDate(LocalDate localDate) {
//        SimpleDateFormat sdf = new SimpleDateFormat(AppConst.DATEFORMAT);

        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }

    public static Map<String, Object> objectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper
                .convertValue(object, new TypeReference<Map<String, Object>>() {});
        return map;
    }

    public static List<Date> startAndEndOfDay(Date date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        LocalDate localDate = localDateTime.toLocalDate();

        // Get start of day
        LocalDateTime startOfDay = localDate.atStartOfDay();
        Date startOfDayDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());

        // Get end of day
        LocalDateTime endOfDay = localDate.atTime(23, 59, 59, 999_000_000);
        Date endOfDayDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        return List.of(startOfDayDate, endOfDayDate);
    }
}
