package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.EventDto;
import com.htetvehiclerental.htetvehiclerental.entity.Event;

public class EventMapper {
        public static EventDto mapToEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setEvent_Id(event.getEvent_Id());
        eventDto.setVehicle(event.getVehicle().getVehicle_id());
        eventDto.setEvent_start_date(event.getEvent_start_date());
        eventDto.setEvent_end_date(event.getEvent_end_date());
        eventDto.setEvent_details(event.getEvent_details());
        return eventDto;
    }
    public static Event maptoEvent(EventDto eventDto) {
        Event event = new Event();
        event.setEvent_Id(eventDto.getEvent_Id());
        event.setEvent_details(eventDto.getEvent_details());
        event.setEvent_start_date(eventDto.getEvent_start_date());
        event.setEvent_end_date(eventDto.getEvent_end_date());
        return event;
    }
}
