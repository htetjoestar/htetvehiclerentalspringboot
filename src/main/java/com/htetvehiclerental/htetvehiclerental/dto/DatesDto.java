package com.htetvehiclerental.htetvehiclerental.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatesDto {
    private LocalDate event_start_date;  
    private LocalDate event_end_date;    
}
