package com.htetvehiclerental.htetvehiclerental.dto;


import java.time.LocalDate;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaintenanceDto {
    private Long maintenance_id;
    private Long vehicle;
    private String maint_status;
    private String details;
    private LocalDate start_date;  
    private LocalDate end_date;  
}
