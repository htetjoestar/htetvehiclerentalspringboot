package com.htetvehiclerental.htetvehiclerental.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicle_id;
    @Column(name = "license_plate")
    private String license_plate;
    @Column(name = "model")
    private String model;
    @Column(name = "brand")
    private String brand;
    @Column(name = "make_year")
    private String make_year;
    @Column(name = "vehicle_status")
    private String vehicle_status;
    @Column(name = "color")
    private String color;
    @Column(name = "type")
    private String type;  
    @Column(name = "fuel")
    private String fuel;   
    @Column(name = "num_seats")
    private int num_seats;
    @Column(name = "base_charge_per_day")
    private int base_charge_per_day;
    @Column(name = "veh_created_date")
    private LocalDateTime veh_created_date;
    @Column(name = "veh_modified_date")
    private LocalDateTime veh_modified_date;
    @Column(name = "veh_last_action")
    private String veh_last_action; 
    @Column(name = "veh_deactivated_date")
    private LocalDateTime veh_deactivated_date;
    @Column(name = "image_url")
    private String image_url;  
}
