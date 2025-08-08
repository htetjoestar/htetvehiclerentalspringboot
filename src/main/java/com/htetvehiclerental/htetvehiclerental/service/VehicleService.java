package com.htetvehiclerental.htetvehiclerental.service;
import com.htetvehiclerental.htetvehiclerental.dto.VehicleDto;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.dto.VehicleFilterRequest;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface VehicleService {
    VehicleDto createVehicle(VehicleDto vehicleDto) throws IOException;
    VehicleDto getVehicleById(Long vehicleId);
    List<VehicleDto> getAllVehicles();
    VehicleDto updateVehicle(Long vehicleId, VehicleDto updatedVehicle);
    List<Vehicle> getFilteredVehicles(VehicleFilterRequest request);
    Page<Vehicle> searchVehicles(VehicleFilterRequest request,Pageable pageable);
    List<Vehicle> getVehiclesByStatus(String status);
    void updateVehicleImage(Long id, String imgUrl);
    List<Vehicle> getAvailableVehicles(String type);
    List<Vehicle> getRandomVehicles();
}