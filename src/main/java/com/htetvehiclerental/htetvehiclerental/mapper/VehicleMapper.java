package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.VehicleDto;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
public class VehicleMapper {
        public static VehicleDto mapToVehicleDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicle_id(vehicle.getVehicle_id());
        vehicleDto.setLicense_plate(vehicle.getLicense_plate());
        vehicleDto.setBrand(vehicle.getBrand());
        vehicleDto.setType(vehicle.getType());
        vehicleDto.setVehicle_status(vehicle.getVehicle_status());
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setMake_year(vehicle.getMake_year());
        vehicleDto.setColor(vehicle.getColor());
        vehicleDto.setNum_seats(vehicle.getNum_seats());
        vehicleDto.setFuel(vehicle.getFuel());
        vehicleDto.setBase_charge_per_day(vehicle.getBase_charge_per_day());
        vehicleDto.setVeh_created_date(vehicle.getVeh_created_date());
        vehicleDto.setVeh_modified_date(vehicle.getVeh_modified_date());
        vehicleDto.setVeh_last_action(vehicle.getVeh_last_action());
        vehicleDto.setVeh_deactivated_date(vehicle.getVeh_deactivated_date());
        vehicleDto.setImage_url(vehicle.getImage_url());
        return vehicleDto;
    }
    public static Vehicle mapToVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id(vehicleDto.getVehicle_id());
        vehicle.setLicense_plate(vehicleDto.getLicense_plate());
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setType(vehicleDto.getType());
        vehicle.setVehicle_status(vehicleDto.getVehicle_status());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setMake_year(vehicleDto.getMake_year());
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setNum_seats(vehicleDto.getNum_seats());
        vehicle.setFuel(vehicleDto.getFuel());
        vehicle.setBase_charge_per_day(vehicleDto.getBase_charge_per_day());
        vehicle.setVeh_created_date(vehicleDto.getVeh_created_date());
        vehicle.setVeh_modified_date(vehicleDto.getVeh_modified_date());
        vehicle.setVeh_last_action(vehicleDto.getVeh_last_action());
        vehicle.setVeh_deactivated_date(vehicleDto.getVeh_deactivated_date());
        vehicle.setImage_url(vehicleDto.getImage_url());

        return vehicle;
    }           

}
