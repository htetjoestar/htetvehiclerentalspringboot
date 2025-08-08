package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.service.MaintenanceService;
import com.htetvehiclerental.htetvehiclerental.dto.MaintenanceDto;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    private MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceDto>> getAllEmployees(){
        List<MaintenanceDto> employees = maintenanceService.getAllMaintenance();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public MaintenanceDto getMaintenanceById(@PathVariable("id") Long maintenanceId) {
        return maintenanceService.getMaintenancebyId(maintenanceId);
    }
    @PostMapping
    public ResponseEntity<MaintenanceDto> createMaintenance(@RequestBody MaintenanceDto maintenanceDto) {
        try {
            MaintenanceDto createdMaintenance = maintenanceService.createMaintenance(maintenanceDto);
            return new ResponseEntity<>(createdMaintenance, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    @PutMapping("{id}")
    public ResponseEntity<MaintenanceDto> updateMaintenance(@PathVariable("id") Long maintenanceID,
                                                    @RequestBody MaintenanceDto updatedMaintenance){
        MaintenanceDto maintenanceDto = maintenanceService.updateMaintenance(maintenanceID, updatedMaintenance);
        return ResponseEntity.ok(maintenanceDto);
    }
@PostMapping("/status")
public ResponseEntity<Page<MaintenanceDto>> filterVehicles(
    @RequestParam(required = false) String status,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String sort
) {
    Pageable pageable;
    if (sort != null && !sort.isEmpty()) {
        String[] sortParams = sort.split(",");
        if (sortParams.length == 2) {
            String sortField = sortParams[0];
            String sortDirection = sortParams[1];
            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        } else {
            pageable = PageRequest.of(page, size);
        }
    } else {
        pageable = PageRequest.of(page, size);
    }

    Page<MaintenanceDto> vehicles = maintenanceService.getVehiclesByStatus(status, pageable);
    return ResponseEntity.ok(vehicles);
}
}
