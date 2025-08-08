package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.dto.EventDto;
import com.htetvehiclerental.htetvehiclerental.service.EventService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;




@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEmployees(){
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }


    @PostMapping
    public ResponseEntity<EventDto> createReservation(@RequestBody EventDto eventDto) {
        try {
            EventDto createdEvent = eventService.createEvent(eventDto);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
}
