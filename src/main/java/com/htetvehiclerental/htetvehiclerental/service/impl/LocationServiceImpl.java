package com.htetvehiclerental.htetvehiclerental.service.impl;

import com.htetvehiclerental.htetvehiclerental.repository.LocationRepository;
import com.htetvehiclerental.htetvehiclerental.mapper.LocationMapper;
import com.htetvehiclerental.htetvehiclerental.dto.LocationDto;
import com.htetvehiclerental.htetvehiclerental.service.LocationService;
import com.htetvehiclerental.htetvehiclerental.entity.Location;

public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;
    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = LocationMapper.mapToLocation(locationDto);
        Location savedLocation = locationRepository.save(location);
        return LocationMapper.mapToLocationDto(savedLocation);
    }

    @Override
    public LocationDto getLocationById(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + locationId));
        return LocationMapper.mapToLocationDto(location);
    }
    
}
