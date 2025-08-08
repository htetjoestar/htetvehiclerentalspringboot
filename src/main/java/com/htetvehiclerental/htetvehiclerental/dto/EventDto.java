package com.htetvehiclerental.htetvehiclerental.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto{
    private Long event_Id;
    private Long vehicle;
    private LocalDate event_start_date;  
    private LocalDate event_end_date;
    private String event_details;    
}
