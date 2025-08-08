package com.htetvehiclerental.htetvehiclerental.service;

import java.io.IOException;
import java.util.List;

import com.htetvehiclerental.htetvehiclerental.dto.EventDto;

public interface EventService {
    EventDto createEvent(EventDto eventDto) throws IOException;
    List<EventDto> getAllEvents();
    EventDto getEventById(Long eventId);

}
