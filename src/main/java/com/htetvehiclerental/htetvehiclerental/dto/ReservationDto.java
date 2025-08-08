package com.htetvehiclerental.htetvehiclerental.dto;

import java.time.LocalDateTime;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto{
    private Long reservation_id;
    private Long customer;
    private Long vehicle;
    private LocalDate pick_up_date;  
    private LocalDate drop_off_date;  
    private LocalDate actual_pick_up_date;  
    private LocalDate actual_drop_off_date;
    private int baby_seat;
    private int insurance;
    private int damages;
    private int late_fee;
    private int total_charge;
    private String res_status;
    private LocalDateTime res_created_date;
    private LocalDateTime res_modified_date; 
    private String res_last_action; 
    private LocalDateTime cancellation_date; 

    private String vehicleBrand;
    private String vehicleModel;
    private String image_url;
    private String make_year;

}
