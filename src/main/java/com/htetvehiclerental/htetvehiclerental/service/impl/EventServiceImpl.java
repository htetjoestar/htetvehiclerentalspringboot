package com.htetvehiclerental.htetvehiclerental.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.htetvehiclerental.htetvehiclerental.dto.EventDto;
import com.htetvehiclerental.htetvehiclerental.entity.Event;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;
import com.htetvehiclerental.htetvehiclerental.mapper.EventMapper;
import com.htetvehiclerental.htetvehiclerental.repository.EventRepository;
import com.htetvehiclerental.htetvehiclerental.repository.VehicleRepository;
import com.htetvehiclerental.htetvehiclerental.service.EventService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{
    private EventRepository eventRepository;
    private VehicleRepository vehicleRepository;
    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = EventMapper.maptoEvent(eventDto);
        Vehicle vehicle = vehicleRepository.findById(eventDto.getVehicle()).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle does not exist with given id: " + eventDto.getVehicle()));
        event.setVehicle(vehicle);
        Event savedEvent = eventRepository.save(event);
        return EventMapper.mapToEventDto(savedEvent);
    }

    @Override
    public EventDto getEventById(Long eventId) {
        Event reservation = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + eventId));
        return EventMapper.mapToEventDto(reservation);
    }
    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map((event) -> EventMapper.mapToEventDto(event))
                .collect(Collectors.toList());
    }
}
