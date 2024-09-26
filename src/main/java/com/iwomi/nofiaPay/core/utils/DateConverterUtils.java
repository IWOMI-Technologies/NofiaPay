package com.iwomi.nofiaPay.core.utils;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
            System.out.println("Failed to parse date: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String convertEpochToTimeFormat(long epochSeconds) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return dateTime.format(formatter);
    }

    public static int[] separateTime(String timeStr) {
        if (timeStr == null || !timeStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Time string must be in the format HH:MM:SS");
        }

        String[] parts = timeStr.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Time string must be in the format HH:MM:SS");
        }

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);
            return new int[]{hours, minutes, seconds};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Time string contains invalid numbers", e);
        }

    }


    public static Date convertToDate(String date) {
        SimpleDateFormat[] dateFormats = {
                new SimpleDateFormat("yyyy/dd/MM"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("MM-dd-yyyy"),
                new SimpleDateFormat("dd-MM-yyyy"),
                new SimpleDateFormat("yyyy-dd-MM"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("MM/dd/yyyy"),
                new SimpleDateFormat("dd/MM/yyyy")

        };

        for (SimpleDateFormat dateFormat : dateFormats) {
            try {
                return dateFormat.parse(date.trim());
            } catch (ParseException e) {
                // continue to next
            }
        }

        throw new IllegalArgumentException("Invalid date format: " + date);
    }
    public static long convertDateToNumeric(String date) {
        Date parsedDate = convertToDate(date);
        return parsedDate.getTime();
    }

}
