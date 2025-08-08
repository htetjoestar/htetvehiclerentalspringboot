package com.htetvehiclerental.htetvehiclerental;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CustomUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(FORMATTER);
    }
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(formatter2);
    }
    
    public static int getDifferenceInDaysInclusive(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Dates must not be null");
        }

        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }
}