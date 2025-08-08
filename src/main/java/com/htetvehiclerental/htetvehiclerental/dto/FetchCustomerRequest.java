
package com.htetvehiclerental.htetvehiclerental.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FetchCustomerRequest {
    private String keyword;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
}
