package com.htetvehiclerental.htetvehiclerental.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.htetvehiclerental.htetvehiclerental.dto.ReportDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationsPerDayDto;
import com.htetvehiclerental.htetvehiclerental.dto.YearMonthDto;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;

public interface ReservationService {
    ReservationDto createReservation(ReservationDto reservationDto) throws IOException;
    ReservationDto getReservationDtobyId(Long reservationId);
    Reservation getReservationbyId(Long reservationId);
    List<ReservationDto> getAllReservation();
    ReservationDto updateReservation(Long reservationId, ReservationDto updatedReservation);
    List<ReservationDto> getReservationsByCustomerId(Long customerId);
    List<ReservationDto> getReservationsByCustomerStatus(Long customerId, String Status);
    Page<ReservationDto> getReservationsByCustomerStatusPageable(Long customerId, String status, Pageable pageable);
    Page<ReservationDto> getReservationsByNextWeek(Pageable pageable);
    Page<ReservationDto> getReservationsByFilters(String status, LocalDate StartDate, LocalDate EndDate,Pageable pageable);
    List<ReportDto> getMonthlyReport();
    ByteArrayInputStream generateMonthlyReportExcel(List<ReportDto> reportData) throws IOException;
    ReportDto getMonthReport(YearMonthDto dto);
    List<ReservationsPerDayDto> getNewReservationsPerDay(YearMonthDto dto);
    List<YearMonthDto> getReservationMonths();
}
