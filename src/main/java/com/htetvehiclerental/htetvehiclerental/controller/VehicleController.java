package com.htetvehiclerental.htetvehiclerental.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.htetvehiclerental.htetvehiclerental.dto.VehicleDto;
import com.htetvehiclerental.htetvehiclerental.dto.VehicleFilterRequest;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.service.VehicleService;

import lombok.AllArgsConstructor;

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
            // Read Azure storage config
            String connectionString = System.getProperty("azure.storage.connection-string");
            if (connectionString == null) {
                connectionString = "your_connection_string_from_properties";
            }
            String containerName = "vehicle-images";

            // Create BlobServiceClient
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            // Get container client
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            // Create container if not exists
            if (!containerClient.exists()) {
                containerClient.create();
            }

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Upload file
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            try (InputStream inputStream = file.getInputStream()) {
                blobClient.upload(inputStream, file.getSize(), true);
            }

            // Get public URL (make sure container has public read access)
            String imgUrl = blobClient.getBlobUrl();

            // Update vehicle with image URL
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
