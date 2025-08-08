package com.htetvehiclerental.htetvehiclerental.dto;



import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportDto {
    private String month;
    private Long totalReservations;
    private Integer totalProfit;
    private Double utilizationRate;
    private Long uniqueCustomers; 
    private Long uniqueVehiclesRented;
    private Long totalRentalDays;    
    private Long vehiclesIdle;
    private Long IdleDays;  
    private Integer potentialProfit;  
    // Getters and setters

    private Integer previousMonthProfit;
    private Integer nextMonthProfit;
}