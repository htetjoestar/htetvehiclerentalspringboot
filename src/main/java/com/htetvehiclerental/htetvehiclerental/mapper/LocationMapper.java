package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.LocationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Location;

public class LocationMapper {
    public static LocationDto mapToLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLocation_id(location.getLocation_id());
        locationDto.setAddress(location.getAddress());
        return locationDto;
    }
    public static Location mapToLocation(LocationDto locationDto) {
        Location location = new LocationDto();
        location.setLocation_id(locationDto.getLocation_id());
        location.setAddress(locationDto.getAddress());
        return location;
    }
}
