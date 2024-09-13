package com.iwomi.nofiaPay.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConverterUtils {

    public static String timeStampToformat(String timestamp, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(timeFormatter);
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
            System.out.println("Failed to parse date: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
