package com.htetvehiclerental.htetvehiclerental.service.impl;

import com.htetvehiclerental.htetvehiclerental.dto.MaintenanceDto;
import com.htetvehiclerental.htetvehiclerental.entity.Maintenance;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.mapper.MaintenanceMapper;
import com.htetvehiclerental.htetvehiclerental.repository.MaintenanceRepository;
import com.htetvehiclerental.htetvehiclerental.repository.VehicleRepository;
import com.htetvehiclerental.htetvehiclerental.service.MaintenanceService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService{
    private MaintenanceRepository maintenanceRepository;
    private VehicleRepository vehicleRepository;

    @Override
    public MaintenanceDto createMaintenance(MaintenanceDto maintenanceDto) {
        Maintenance maintenance = MaintenanceMapper.mapToReservation(maintenanceDto);
        
        Vehicle vehicle = vehicleRepository.findById(maintenanceDto.getVehicle()).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle does not exist with given id: " + maintenanceDto.getVehicle()));
        maintenance.setVehicle(vehicle);
        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);
        return MaintenanceMapper.mapToMaintenanceDto(savedMaintenance);
    }

    @Override
    public MaintenanceDto getMaintenancebyId(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + maintenanceId));
        return MaintenanceMapper.mapToMaintenanceDto(maintenance);
    }
    @Override
    public List<MaintenanceDto> getAllMaintenance() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        return maintenances.stream().map((maintenance) -> MaintenanceMapper.mapToMaintenanceDto(maintenance))
                .collect(Collectors.toList());
    }
    @Override
    public MaintenanceDto updateMaintenance(Long maintenanceId, MaintenanceDto updatedMaintenance) {
        Maintenance existingMaintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + maintenanceId));
        existingMaintenance.setDetails(updatedMaintenance.getDetails());
        existingMaintenance.setMaint_status(updatedMaintenance.getMaint_status());
        existingMaintenance.setStart_date(updatedMaintenance.getStart_date());
        existingMaintenance.setEnd_date(updatedMaintenance.getEnd_date());

        Maintenance savedMaintenance = maintenanceRepository.save(existingMaintenance);
        return MaintenanceMapper.mapToMaintenanceDto(savedMaintenance);
    }
    @Override
    public Page<MaintenanceDto> getVehiclesByStatus(String status, Pageable pageable) {
        System.out.println("Fetching maintenances with status: " + status);
        Page<Maintenance> maintenances = maintenanceRepository.findByStatus(status, pageable);
        return maintenances.map(MaintenanceMapper::mapToMaintenanceDto);
    }
}
