package com.htetvehiclerental.htetvehiclerental.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDto {
    private Long invoice_id;
    private Long reservation;
    private Long vehicle;
    private String damageDescription;
    private LocalDateTime invoice_created_date;
    private boolean paid;

    private LocalDate pick_up_date;
    private LocalDate drop_off_date;
    private int late_fee;
    private int damages;    

    private String vehicleBrand;
    private String vehicleModel;
    private String license_plate;
    private String image_url;
    private String make_year;    
}
