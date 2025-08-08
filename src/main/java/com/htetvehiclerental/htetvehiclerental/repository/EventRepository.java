package com.htetvehiclerental.htetvehiclerental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htetvehiclerental.htetvehiclerental.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
