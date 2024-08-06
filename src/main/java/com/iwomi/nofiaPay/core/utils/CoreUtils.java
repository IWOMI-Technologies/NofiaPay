package com.iwomi.nofiaPay.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
}
