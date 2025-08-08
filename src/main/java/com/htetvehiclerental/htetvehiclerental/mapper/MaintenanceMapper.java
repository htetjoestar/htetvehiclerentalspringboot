package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.MaintenanceDto;
import com.htetvehiclerental.htetvehiclerental.entity.Maintenance;

public class MaintenanceMapper {
    public static MaintenanceDto mapToMaintenanceDto(Maintenance maintenance) {
        MaintenanceDto maintenanceDto = new MaintenanceDto();
        maintenanceDto.setMaintenance_id(maintenance.getMaintenance_id());
        maintenanceDto.setVehicle(maintenance.getVehicle().getVehicle_id());
        maintenanceDto.setMaint_status(maintenance.getMaint_status());
        maintenanceDto.setDetails(maintenance.getDetails());
        maintenanceDto.setStart_date(maintenance.getStart_date());
        maintenanceDto.setEnd_date(maintenance.getEnd_date());
        return maintenanceDto;

    }
    public static Maintenance mapToReservation(MaintenanceDto maintenanceDto) {
        Maintenance maintenance = new Maintenance();
        maintenance.setMaintenance_id(maintenanceDto.getMaintenance_id());
        maintenance.setMaint_status(maintenanceDto.getMaint_status());
        maintenance.setDetails(maintenanceDto.getDetails());
        maintenance.setStart_date(maintenanceDto.getStart_date());
        maintenance.setEnd_date(maintenanceDto.getEnd_date());

        
        return maintenance;
    }
}