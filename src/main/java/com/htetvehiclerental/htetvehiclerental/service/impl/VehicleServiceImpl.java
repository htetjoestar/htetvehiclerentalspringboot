package com.htetvehiclerental.htetvehiclerental.service.impl;

import com.htetvehiclerental.htetvehiclerental.dto.VehicleDto;
import com.htetvehiclerental.htetvehiclerental.dto.VehicleFilterRequest;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;
import com.htetvehiclerental.htetvehiclerental.mapper.VehicleMapper;
import com.htetvehiclerental.htetvehiclerental.repository.VehicleRepository;
import com.htetvehiclerental.htetvehiclerental.service.VehicleService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = VehicleMapper.mapToVehicle(vehicleDto);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return VehicleMapper.mapToVehicleDto(savedVehicle);
    }
    @Override
    public List<VehicleDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream().map((vehicle) -> VehicleMapper.mapToVehicleDto(vehicle))
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDto getVehicleById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));
        return VehicleMapper.mapToVehicleDto(vehicle);
    }
    @Override
    public VehicleDto updateVehicle(Long vehicleId, VehicleDto updatedVehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));
        existingVehicle.setLicense_plate(updatedVehicle.getLicense_plate());
        existingVehicle.setType(updatedVehicle.getType());
        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setModel(updatedVehicle.getModel());
        existingVehicle.setMake_year(updatedVehicle.getMake_year());
        existingVehicle.setColor(updatedVehicle.getColor());
        existingVehicle.setNum_seats(updatedVehicle.getNum_seats());
        existingVehicle.setFuel(updatedVehicle.getFuel());
        existingVehicle.setBase_charge_per_day(updatedVehicle.getBase_charge_per_day());
        existingVehicle.setVehicle_status(updatedVehicle.getVehicle_status());
        existingVehicle.setVeh_modified_date(updatedVehicle.getVeh_modified_date());
        existingVehicle.setVeh_created_date(updatedVehicle.getVeh_created_date());
        existingVehicle.setVeh_deactivated_date(updatedVehicle.getVeh_deactivated_date());
        existingVehicle.setVeh_last_action(updatedVehicle.getVeh_last_action());
        Vehicle savedVehicle = vehicleRepository.save(existingVehicle);
        return VehicleMapper.mapToVehicleDto(savedVehicle);
}
    @Override
    public List<Vehicle> getFilteredVehicles(VehicleFilterRequest request) {
        return vehicleRepository.findAvailableFilteredVehicles(
            request.getColor(),
            request.getNum_seats(),
            request.getTypes(),
            request.getEvent_start_date(),
            request.getEvent_end_date()
        );
    }

    @Override
    public Page<Vehicle> searchVehicles(VehicleFilterRequest request,Pageable pageable) {
        return vehicleRepository.searchWithFilters(
            request.getKeyword(),
            request.getVehicle_status(),
            request.getNum_seats(),
            request.getType(),
            pageable
        );
    }
    @Override
    public List<Vehicle> getVehiclesByStatus(String status) {
        return vehicleRepository.findByStatus(status);
    }
    @Override
    public void updateVehicleImage(Long id, String imgUrl) {
    Vehicle vehicle = vehicleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

    vehicle.setImage_url(imgUrl);
    vehicleRepository.save(vehicle);
    }
    @Override
    public List<Vehicle> getAvailableVehicles(String type) {
        return vehicleRepository.findAvailableVehiclesWithoutFutureReservations(type);
    }

        @Override
    public List<Vehicle> getRandomVehicles() {
        return vehicleRepository.findTenRandomVehicles();
    }
}