package com.htetvehiclerental.htetvehiclerental.dto;

public class YearMonthDto {
    private int year;
    private int month;

    public YearMonthDto(Object year, Object month) {
        this.year = year != null ? ((Number) year).intValue() : 0;
        this.month = month != null ? ((Number) month).intValue() : 0;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
