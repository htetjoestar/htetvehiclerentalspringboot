package com.htetvehiclerental.htetvehiclerental.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.htetvehiclerental.htetvehiclerental.dto.MaintenanceDto;


public interface MaintenanceService {
    MaintenanceDto createMaintenance(MaintenanceDto maintenanceDto) throws IOException;
    MaintenanceDto getMaintenancebyId(Long maintenanceId);
    List<MaintenanceDto> getAllMaintenance();
    MaintenanceDto updateMaintenance(Long maintenanceId, MaintenanceDto updatedMaintenance);
    Page<MaintenanceDto> getVehiclesByStatus(String status, Pageable pageable);
}
