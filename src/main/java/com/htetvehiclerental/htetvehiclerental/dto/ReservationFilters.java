package com.htetvehiclerental.htetvehiclerental.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationFilters {
    private Long customer_id;
    private String res_status;
    private LocalDate startDate;
    private LocalDate endDate;
}
