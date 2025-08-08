package com.htetvehiclerental.htetvehiclerental.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Getter
@NoArgsConstructor
@Setter
public class ReservationsPerDayDto {
    private LocalDate date;
    private Long count;

    
 public ReservationsPerDayDto(Object dateObj, Long count) {
        if (dateObj instanceof java.sql.Date) {
            this.date = ((java.sql.Date) dateObj).toLocalDate();
        } else if (dateObj instanceof java.time.LocalDate) {
            this.date = (LocalDate) dateObj;
        } else {
            throw new IllegalArgumentException("Unexpected date type: " + dateObj.getClass());
        }
        this.count = count;
    }
}