package com.htetvehiclerental.htetvehiclerental.service;

import com.htetvehiclerental.htetvehiclerental.dto.LocationDto;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
    LocationDto getLocationById(Long locationId);
}
