package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.dto.VehicleDto;
import com.htetvehiclerental.htetvehiclerental.dto.VehicleFilterRequest;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.service.VehicleService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.nio.file.Path;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private static final String IMAGE_UPLOAD_DIR = "uploads/images/";

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(value = "/upload")
    public ResponseEntity<VehicleDto> createVehicle(
            @RequestPart("data") VehicleDto vehicleDto
    ) {
        try {
            System.out.println("Received vehicle data: " + vehicleDto.getLicense_plate());
            VehicleDto savedVehicle = vehicleService.createVehicle(vehicleDto);
            return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
        List<VehicleDto> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }    

    @GetMapping("{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable("id") Long vehicleId){
        VehicleDto vehicleDto = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(vehicleDto);
    }    

    @PutMapping("{id}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable("id") Long vehicleId,
                                                    @RequestBody VehicleDto updatedVehicle){
        VehicleDto vehicleDto = vehicleService.updateVehicle(vehicleId, updatedVehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Vehicle>> filterVehicles(@RequestBody VehicleFilterRequest request) {
        List<Vehicle> vehicles = vehicleService.getFilteredVehicles(request);
        return ResponseEntity.ok(vehicles);
    }

@PostMapping("/adminfilter")
public ResponseEntity<Page<Vehicle>> searchVehicles(
    @RequestBody VehicleFilterRequest request,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String sort // e.g., sort=num_seats,desc
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

    Page<Vehicle> vehicles = vehicleService.searchVehicles(request, pageable);
    return ResponseEntity.ok(vehicles);
}

    @PostMapping("/status/{status}")
    public ResponseEntity<List<Vehicle>> filterVehicles(@PathVariable("status") String status) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByStatus(status);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id,
                                              @RequestParam("image") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, file.getBytes());

            String imgUrl = "/images/" + fileName; // This should match your static resource mapping
            vehicleService.updateVehicleImage(id, imgUrl);

            return ResponseEntity.ok(imgUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }
    
    @GetMapping("/available/{status}")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles(@PathVariable("status") String type) {
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles(type);
        return ResponseEntity.ok(availableVehicles);
    }

        @GetMapping("/random")
    public ResponseEntity<List<Vehicle>> getRandomVehicles() {
        List<Vehicle> vehicles = vehicleService.getRandomVehicles();
        return ResponseEntity.ok(vehicles);
    }
}
