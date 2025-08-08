package com.htetvehiclerental.htetvehiclerental.dto;

import java.util.List;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleFilterRequest {
    private String color;
    private Integer num_seats;
    private String type;
    private List<String> types;
    private String keyword;
    private String vehicle_status;

    private LocalDate event_start_date;  
    private LocalDate event_end_date;
}
