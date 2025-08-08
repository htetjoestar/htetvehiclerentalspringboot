package com.htetvehiclerental.htetvehiclerental.service.impl;

import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

import com.htetvehiclerental.htetvehiclerental.dto.ReportDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationsPerDayDto;
import com.htetvehiclerental.htetvehiclerental.dto.YearMonthDto;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;
import com.htetvehiclerental.htetvehiclerental.mapper.ReservationMapper;
import com.htetvehiclerental.htetvehiclerental.repository.CustomerRepository;
import com.htetvehiclerental.htetvehiclerental.repository.ReservationRepository;
import com.htetvehiclerental.htetvehiclerental.repository.VehicleRepository;
import com.htetvehiclerental.htetvehiclerental.service.ReservationService;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    private CustomerRepository customerRepository;
    private VehicleRepository vehicleRepository;
    private ReservationRepository reservationRepository;
    
    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = ReservationMapper.mapToReservation(reservationDto);
        Customer customer = customerRepository.findById(reservationDto.getCustomer()).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle does not exist with given id: " + reservationDto.getCustomer()));
        reservation.setCustomer(customer);
        Vehicle vehicle = vehicleRepository.findById(reservationDto.getVehicle()).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle does not exist with given id: " + reservationDto.getVehicle()));
        reservation.setVehicle(vehicle);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.mapToReservationDto(savedReservation);
    }
    @Override
    public ReservationDto getReservationDtobyId(Long maintenanceId) {
        Reservation reservation = reservationRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + maintenanceId));
        return ReservationMapper.mapToReservationDto(reservation);
    }
    @Override
    public Reservation getReservationbyId(Long maintenanceId) {
        Reservation reservation = reservationRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + maintenanceId));
        return reservation;
    }
    @Override
    public List<ReservationDto> getAllReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map((reservation) -> ReservationMapper.mapToReservationDto(reservation))
                .collect(Collectors.toList());
    }
    @Override
    public ReservationDto updateReservation(Long reservationId, ReservationDto updatedMaintenance) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + reservationId));
        existingReservation.setActual_pick_up_date(updatedMaintenance.getActual_pick_up_date());
        existingReservation.setActual_drop_off_date(updatedMaintenance.getActual_drop_off_date());
        existingReservation.setBaby_seat(updatedMaintenance.getBaby_seat());
        existingReservation.setInsurance(updatedMaintenance.getInsurance());
        existingReservation.setDamages(updatedMaintenance.getDamages());
        existingReservation.setLate_fee(updatedMaintenance.getLate_fee());
        existingReservation.setTotal_charge(updatedMaintenance.getTotal_charge());
        existingReservation.setRes_status(updatedMaintenance.getRes_status());
        existingReservation.setRes_last_action(updatedMaintenance.getRes_last_action());
        existingReservation.setCancellation_date(updatedMaintenance.getCancellation_date());
        existingReservation.setRes_modified_date(updatedMaintenance.getRes_modified_date());
        existingReservation.setRes_created_date(updatedMaintenance.getRes_created_date());
        existingReservation.setPick_up_date(updatedMaintenance.getPick_up_date());
        existingReservation.setDrop_off_date(updatedMaintenance.getDrop_off_date());


        Reservation savedReservation = reservationRepository.save(existingReservation);
        return ReservationMapper.mapToReservationDto(savedReservation);
    }
    @Override
    public List<ReservationDto> getReservationsByCustomerId(Long customerId) {
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        return reservations.stream()
                .map((reservation) -> ReservationMapper.mapToReservationDto(reservation))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByCustomerStatus(Long customerId, String status) {
        List<Reservation> reservations = reservationRepository.findByCustomerStatus(customerId,status);
        return reservations.stream()
                .map((reservation) -> ReservationMapper.mapToReservationDto(reservation))
                .collect(Collectors.toList());
    }    
    @Override
    public Page<ReservationDto> getReservationsByCustomerStatusPageable(Long customerId, String status, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByCustomerStatusPageable(customerId,status,pageable);
        return reservations.map(ReservationMapper::mapToReservationDto);
    } 
    @Override
    public Page<ReservationDto> getReservationsByNextWeek(Pageable pageable){
        Page<Reservation> reservations = reservationRepository.findNextWeek(LocalDate.now(),LocalDate.now().plusWeeks(1),pageable);
        return reservations.map(ReservationMapper::mapToReservationDto);       
    }

    @Override
    public Page<ReservationDto> getReservationsByFilters(String status, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByFilters(status, startDate, endDate, pageable);

        return reservations.map(ReservationMapper::mapToReservationDto);
    }
private int getMonthlyProfit(YearMonth ym) {
    LocalDate start = ym.atDay(1);
    LocalDate end = ym.atEndOfMonth();
    List<Reservation> reservations = reservationRepository.findByActualPickUpDateBetween(start, end);
    return reservations.stream().mapToInt(Reservation::getTotal_charge).sum();
}
@Override
public ReportDto getMonthReport(YearMonthDto dto) {
    YearMonth ym = YearMonth.of(dto.getYear(), dto.getMonth());
    LocalDate start = ym.atDay(1);
    LocalDate end = ym.atEndOfMonth();
    String month = ym.toString(); // e.g., "2025-06"

    List<Reservation> reservations = reservationRepository.findByActualPickUpDateBetween(start, end);
    List<Vehicle> vehicles = vehicleRepository.findAll();
    int totalVehicles = (int) vehicleRepository.count();

    LocalDateTime endOfMonth = end.atTime(23, 59, 59);

    long potentialProfit = vehicles.stream()
        .filter(v -> v.getVeh_created_date() != null && !v.getVeh_created_date().isAfter(endOfMonth))
        .mapToLong(v -> (long) v.getBase_charge_per_day() * ym.lengthOfMonth())
        .sum();

    long totalRentalDays = reservations.stream()
        .mapToLong(r -> ChronoUnit.DAYS.between(r.getActual_pick_up_date(), r.getActual_drop_off_date()))
        .sum();

    long totalProfit = reservations.stream()
        .mapToLong(Reservation::getTotal_charge)
        .sum();

    long reservationCount = reservations.size();

    long uniqueVehiclesRented = reservations.stream()
        .map(r -> r.getVehicle().getVehicle_id())
        .filter(Objects::nonNull)
        .distinct()
        .count();

    long uniqueCustomers = reservations.stream()
        .map(r -> r.getCustomer().getCustomer_id())
        .filter(Objects::nonNull)
        .distinct()
        .count();

    int daysInMonth = ym.lengthOfMonth();
    long availableDays = totalVehicles * daysInMonth;
    long idleVehicles = totalVehicles - uniqueVehiclesRented;
    long idleDays = availableDays - totalRentalDays;

    double utilizationRate = availableDays > 0
        ? Math.round((double) totalRentalDays / availableDays * 1000) / 10.0
        : 0.0;
    // Calculate previous and next month profits
    Integer previousMonthProfit = null;
    Integer nextMonthProfit = null;

    try {
        YearMonth prevYm = ym.minusMonths(1);
        previousMonthProfit = getMonthlyProfit(prevYm);
    } catch (Exception e) {
        previousMonthProfit = null;
    }

    try {
        YearMonth nextYm = ym.plusMonths(1);
        nextMonthProfit = getMonthlyProfit(nextYm);
    } catch (Exception e) {
        nextMonthProfit = null;
    }
    return new ReportDto(
        month,
        reservationCount,
        (int) totalProfit,
        utilizationRate,
        uniqueCustomers,
        uniqueVehiclesRented,
        totalRentalDays,
        idleVehicles,
        idleDays,
        (int) potentialProfit,
        previousMonthProfit,
        nextMonthProfit
    );
}
@Override
public List<ReportDto> getMonthlyReport() {
    int totalVehicles = (int) vehicleRepository.count();
    List<Vehicle> vehicles = vehicleRepository.findAll();

    Map<String, Long> totalProfitMap = mapFromList(reservationRepository.getTotalChargePerMonth());
    Map<String, Long> totalReservationsMap = mapFromList(reservationRepository.getReservationCountPerMonth());
    Map<String, Long> rentalDaysMap = mapFromList(reservationRepository.getRentalDaysPerMonth());
    Map<String, Long> uniqueCustomersMap = mapFromList(reservationRepository.getUniqueCustomersPerMonth());
    Map<String, Long> uniqueVehiclesMap = mapFromList(reservationRepository.getUniqueVehiclesPerMonth());
    Set<String> months = new HashSet<>(totalReservationsMap.keySet());
    months.addAll(totalProfitMap.keySet());
    months.addAll(rentalDaysMap.keySet());

    // New: Calculate potential profit per month
    Map<String, Long> potentialProfitMap = new HashMap<>();
    for (String month : months) {
        YearMonth ym = YearMonth.parse(month);
        int daysInMonth = ym.lengthOfMonth();

        long potentialProfit = vehicles.stream()
            .mapToLong(v -> (long) v.getBase_charge_per_day() * daysInMonth)
            .sum();

        potentialProfitMap.put(month, potentialProfit);
    }


    List<ReportDto> reportList = new ArrayList<>();

    for (String month : months) {
        long potentialProfit = potentialProfitMap.getOrDefault(month, 0L);
        long totalRentalDays = rentalDaysMap.getOrDefault(month, 0L);
        long totalProfit = totalProfitMap.getOrDefault(month, 0L);
        long reservationCount = totalReservationsMap.getOrDefault(month, 0L);
        long uniqueVehiclesRented = uniqueVehiclesMap.getOrDefault(month, 0L);
        long uniqueCustomers = uniqueCustomersMap.getOrDefault(month, 0L);

        YearMonth ym = YearMonth.parse(month);
        int daysInMonth = ym.lengthOfMonth();
        long availableDays = totalVehicles * daysInMonth;
        long idleVehicles = totalVehicles - uniqueVehiclesRented;
        long idleDays = availableDays - totalRentalDays;

        double utilizationRate = availableDays > 0
            ? Math.round((double) totalRentalDays / availableDays * 1000) / 10.0
            : 0.0;
    // Calculate previous and next month profits
    Integer previousMonthProfit = null;
    Integer nextMonthProfit = null;
        reportList.add(new ReportDto(
            month,
            reservationCount,
            (int) totalProfit,
            utilizationRate,
            uniqueCustomers,
            uniqueVehiclesRented,
            totalRentalDays,
            idleVehicles,
            idleDays,
            (int) potentialProfit,
        previousMonthProfit,
        nextMonthProfit
        ));
    }

    reportList.sort(Comparator.comparing(ReportDto::getMonth).reversed());
    return reportList;
}

// Helper method
private Map<String, Long> mapFromList(List<Object[]> results) {
    return results.stream()
        .collect(Collectors.toMap(
            r -> (String) r[0],
            r -> r[1] != null ? ((Number) r[1]).longValue() : 0L
        ));
}


public ByteArrayInputStream generateMonthlyReportExcel(List<ReportDto> reportData) throws IOException {
    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("Monthly Report");

        // Create styles
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Create styles
        CellStyle headerStyle2 = workbook.createCellStyle();
        Font headerFont2 = workbook.createFont();
        headerFont2.setBold(true);
        headerFont2.setColor(IndexedColors.RED.getIndex());
        headerStyle2.setFont(headerFont2);
        headerStyle2.setBorderTop(BorderStyle.THIN);
        headerStyle2.setBorderBottom(BorderStyle.THIN);
        headerStyle2.setBorderLeft(BorderStyle.THIN);
        headerStyle2.setBorderRight(BorderStyle.THIN);
        headerStyle2.setAlignment(HorizontalAlignment.CENTER);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        CellStyle currencyStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        currencyStyle.setDataFormat(format.getFormat("$#,##0.00"));
        currencyStyle.setBorderTop(BorderStyle.THIN);
        currencyStyle.setBorderBottom(BorderStyle.THIN);
        currencyStyle.setBorderLeft(BorderStyle.THIN);
        currencyStyle.setBorderRight(BorderStyle.THIN);

        // Header Row
        Row header = sheet.createRow(0);
        String[] headers = { "Month", "Total Reservations" ,"Unique Customers","Unique Vehicles","Unutilized Vehicles", "Total Rental Days","Total Idle Days", "Utilization Rate (%)", "Total Profit"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);

            cell.setCellValue(headers[i]);
            if(headers[i].equals("Unutilized Vehicles") || headers[i].equals("Total Idle Days")){
                cell.setCellStyle(headerStyle2);
            }else{
                cell.setCellStyle(headerStyle);
            }            
        }

        // Data Rows
        int rowIdx = 1;
        for (ReportDto report : reportData) {
            Row row = sheet.createRow(rowIdx++);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(report.getMonth());
            cell0.setCellStyle(cellStyle);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(report.getTotalReservations());
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(report.getUniqueCustomers());
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(report.getUniqueVehiclesRented());
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(report.getVehiclesIdle());
            cell4.setCellStyle(cellStyle); 

            Cell cell5 = row.createCell(5);
            cell5.setCellValue(report.getTotalRentalDays());
            cell5.setCellStyle(cellStyle);   


            Cell cell6 = row.createCell(6);
            cell6.setCellValue(report.getIdleDays());
            cell6.setCellStyle(cellStyle);   

            Cell cell7 = row.createCell(7);
            cell7.setCellValue(report.getUtilizationRate());
            cell7.setCellStyle(cellStyle);            
            
            Cell cell8 = row.createCell(8);
            cell8.setCellValue(report.getTotalProfit());
            cell8.setCellStyle(currencyStyle);


                      
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
@Override
public List<ReservationsPerDayDto> getNewReservationsPerDay(YearMonthDto dto) {
    YearMonth ym = YearMonth.of(dto.getYear(), dto.getMonth());
    LocalDate startDate = ym.atDay(1);
    LocalDate endDate = ym.atEndOfMonth();

    LocalDateTime start = startDate.atStartOfDay(); // 00:00
    LocalDateTime end = endDate.atTime(23, 59, 59); // 23:59:59

    List<ReservationsPerDayDto> rawData = reservationRepository.countNewReservationsPerDayBetween(start, end);

    Map<LocalDate, Long> countMap = rawData.stream()
            .collect(Collectors.toMap(ReservationsPerDayDto::getDate, ReservationsPerDayDto::getCount));

    List<ReservationsPerDayDto> result = new ArrayList<>();
    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
        result.add(new ReservationsPerDayDto(date, countMap.getOrDefault(date, 0L)));
    }

    return result;
}

    @Override
    public List<YearMonthDto> getReservationMonths() {
        return reservationRepository.findDistinctReservationMonths();
    }
}
