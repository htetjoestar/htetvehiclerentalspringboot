package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.entity.Location;
import com.htetvehiclerental.htetvehiclerental.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/location")
public class LocationController {
    private final LocationRepository repo;

    @GetMapping
    public List<Location> getAllUsers() {
        return repo.findAll();
    }

    @PostMapping
    public Location createUser(@RequestBody Location location) {
        return repo.save(location);
    }
}
