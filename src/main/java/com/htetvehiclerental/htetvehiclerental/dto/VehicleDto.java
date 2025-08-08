package com.htetvehiclerental.htetvehiclerental.dto;

import java.time.LocalDateTime;

import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto extends Vehicle {
    private Long vehicle_id;
    private String license_plate;
    private String model;
    private String brand;
    private String make_year;
    private String vehicle_status;
    private String color;
    private String type;    
    private String fuel;
    private int base_charge_per_day;
    private LocalDateTime veh_created_date;
    private LocalDateTime veh_modified_date;
    private String veh_last_action; 
    private LocalDateTime veh_deactivated_date;
    private String image_url;  
}
