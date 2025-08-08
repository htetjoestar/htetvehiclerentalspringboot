package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.CustomUtils;
import com.htetvehiclerental.htetvehiclerental.dto.ReportDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationFilters;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationsPerDayDto;
import com.htetvehiclerental.htetvehiclerental.dto.YearMonthDto;
import com.htetvehiclerental.htetvehiclerental.entity.Invoice;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;
import com.htetvehiclerental.htetvehiclerental.repository.ReservationRepository;
import com.htetvehiclerental.htetvehiclerental.service.InvoiceService;
import com.htetvehiclerental.htetvehiclerental.service.ReservationService;
import com.lowagie.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;

import java.awt.Color;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.htetvehiclerental.htetvehiclerental.CustomUtils;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private ReservationService reservationService;
    private ReservationRepository reservationRepository;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllEmployees(){
        List<ReservationDto> reservations = reservationService.getAllReservation();
        return ResponseEntity.ok(reservations);
    }


    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        try {
            ReservationDto createdReservation = reservationService.createReservation(reservationDto);
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    @GetMapping("/{id}")
    public ReservationDto getReservationbyId(@PathVariable("id") Long maintenanceId) {
        return reservationService.getReservationDtobyId(maintenanceId);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable("id") Long reservationId,
                                                    @RequestBody ReservationDto updatedReservation){
        ReservationDto maintenanceDto = reservationService.updateReservation(reservationId, updatedReservation);
        return ResponseEntity.ok(maintenanceDto);
    }

    @PostMapping("/customer/{id}")
    public ResponseEntity<List<ReservationDto>> getReservationsByCustomer(@PathVariable("id") Long customerId) {
        List<ReservationDto> reservations = reservationService.getReservationsByCustomerId(customerId);
        return ResponseEntity.ok(reservations); // Convert the list to a string for response
    }


    @PostMapping("/status")
    public ResponseEntity<List<ReservationDto>> filterVehicles(@RequestBody ReservationFilters reservationDto) {
        List<ReservationDto> reservation = reservationService.getReservationsByCustomerStatus(reservationDto.getCustomer_id(), reservationDto.getRes_status());
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/statuspage")
    public ResponseEntity<Page<ReservationDto>> statusPage(
            @RequestBody ReservationFilters reservationFilters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable;

        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                String sortField = sortParams[0];
                String sortDirection = sortParams[1];
                Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
            } else {
                pageable = PageRequest.of(page, size);
            }
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<ReservationDto> reservations = reservationService.getReservationsByCustomerStatusPageable(
                reservationFilters.getCustomer_id(),
                reservationFilters.getRes_status(),
                pageable
        );

        return ResponseEntity.ok(reservations);
    }    
    
        @PostMapping("/nextweek")
        public ResponseEntity<Page<ReservationDto>> findNextWeek(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(required = false) String sort
        ) {
            Pageable pageable;

            if (sort != null && !sort.isEmpty()) {
                String[] sortParams = sort.split(",");
                if (sortParams.length == 2) {
                    String sortField = sortParams[0];
                    String sortDirection = sortParams[1];
                    Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;
                    pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
                } else {
                    pageable = PageRequest.of(page, size);
                }
            } else {
                pageable = PageRequest.of(page, size);
            }

            Page<ReservationDto> reservations = reservationService.getReservationsByNextWeek(pageable);
            return ResponseEntity.ok(reservations);
        }

    @PostMapping("/filter")
    public ResponseEntity<Page<ReservationDto>> filter(
            @RequestBody ReservationFilters reservationFilters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable;

        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                String sortField = sortParams[0];
                String sortDirection = sortParams[1];
                Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
            } else {
                pageable = PageRequest.of(page, size);
            }
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<ReservationDto> reservations = reservationService.getReservationsByFilters(
                reservationFilters.getRes_status(),
                reservationFilters.getStartDate(),
                reservationFilters.getEndDate(),
                pageable
        );

        return ResponseEntity.ok(reservations);
    }


    @GetMapping("/report/monthly")
    public ResponseEntity<Resource> downloadMonthlyReport() throws IOException {

    List<ReportDto> report = reservationService.getMonthlyReport();
    ByteArrayInputStream in = reservationService.generateMonthlyReportExcel(report);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=monthly_reservation_report.xlsx");

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(new InputStreamResource(in));
    }        

    @PostMapping("/monthly/current")
    public ResponseEntity<ReportDto> getCurrentMonthReport(@RequestBody YearMonthDto dto) {
        ReportDto report = reservationService.getMonthReport(dto);
        return ResponseEntity.ok(report);
    }

    
@PostMapping("/created-per-day")
public List<ReservationsPerDayDto> getNewReservationsPerDay(@RequestBody YearMonthDto dto) {
    return reservationService.getNewReservationsPerDay(dto);
}
    @GetMapping("/months")
    public ResponseEntity<List<YearMonthDto>> getReservationMonths() {
        List<YearMonthDto> months = reservationService.getReservationMonths();
        return ResponseEntity.ok(months);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generateSimplePdf(@PathVariable("id") Long reservation_id) {
        Reservation invoiceDto = reservationService.getReservationbyId(reservation_id); // Example reservation ID, replace as needed

        Integer diffDays = CustomUtils.getDifferenceInDaysInclusive(invoiceDto.getDrop_off_date(), invoiceDto.getDrop_off_date());
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            // Create PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.addTitle("Receipt");
            document.add(new Paragraph("Reservation created: " + CustomUtils.formatLocalDate(invoiceDto.getRes_created_date().toLocalDate()), boldFont));
            document.add(new Paragraph("Bill To: " + invoiceDto.getCustomer().getCust_first_name() + " " + invoiceDto.getCustomer().getCust_last_name(), boldFont));
            document.add(new Paragraph("             " + invoiceDto.getCustomer().getCust_email(), boldFont));
            document.add(new Paragraph("From: Htet Vehicle Rentals", boldFont));
            document.add(new Paragraph("Receipt for reservation.", boldFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Dear Customer,"));
            document.add(new Paragraph("Thank you for choosing Htet Vehicle Rentals."));
            document.add(new Paragraph("Here is your receipt for the rental from " + CustomUtils.formatLocalDate(invoiceDto.getPick_up_date()) + " to " + CustomUtils.formatLocalDate(invoiceDto.getDrop_off_date())));
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Chunk("You rented a ", normalFont));
            paragraph.add(new Chunk(invoiceDto.getVehicle().getBrand() + " ", boldFont));
            paragraph.add(new Chunk(invoiceDto.getVehicle().getModel() + " ", boldFont));
            paragraph.add(new Chunk(invoiceDto.getVehicle().getMake_year() + " ", boldFont));
            paragraph.add(new Chunk("with license plate ", normalFont));
            paragraph.add(new Chunk(invoiceDto.getVehicle().getLicense_plate() + ".", boldFont));
            document.add(paragraph);

            Table myTable = new Table(2);
            myTable.setWidth(100);
            myTable.setPadding(5f);
            myTable.setSpacing(5f);
            myTable.setWidths(new float[]{70f, 30f});  // 70% width for Charge Description, 30% for Amount

            ArrayList<String> headerTable = new ArrayList<>();
            headerTable.add("Charge Description");
            headerTable.add("Amount");

            headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHeader(true);
            myTable.addCell(current);
        });     Paragraph baseChargeParagraph = new Paragraph();
                baseChargeParagraph.add(new Phrase("Base Charge\n", normalFont));
                baseChargeParagraph.add(new Phrase("Rented from " + CustomUtils.formatLocalDate(invoiceDto.getPick_up_date()) + " to " + CustomUtils.formatLocalDate(invoiceDto.getDrop_off_date()), normalFont));
                myTable.addCell(new Cell(baseChargeParagraph));
                myTable.addCell(new Cell(new Phrase("$" + String.valueOf(invoiceDto.getTotal_charge()), normalFont)));
            if(invoiceDto.getBaby_seat() > 0) {
                Paragraph lateFeeParagraph = new Paragraph();
                lateFeeParagraph.add(new Phrase("Baby Seat fee\n", normalFont));
                lateFeeParagraph.add(new Phrase(invoiceDto.getBaby_seat() + " seats", normalFont));
                myTable.addCell(new Cell(lateFeeParagraph));
                myTable.addCell(new Cell(new Phrase("$" + String.valueOf(invoiceDto.getBaby_seat() * 100), normalFont)));
            }
            if(invoiceDto.getInsurance() > 0) {
                Paragraph lateFeeParagraph = new Paragraph();
                lateFeeParagraph.add(new Phrase("Insurance fee\n", normalFont));
                myTable.addCell(new Cell(lateFeeParagraph));
                myTable.addCell(new Cell(new Phrase("$" + String.valueOf(invoiceDto.getInsurance()), normalFont)));
            }            
            if(invoiceDto.getLate_fee() > 0) {
                Paragraph lateFeeParagraph = new Paragraph();
                lateFeeParagraph.add(new Phrase("Late Fee\n", normalFont));
                lateFeeParagraph.add(new Phrase(diffDays + " days late", normalFont));
                myTable.addCell(new Cell(lateFeeParagraph));
                myTable.addCell(new Cell(new Phrase("$" + String.valueOf(invoiceDto.getLate_fee()), normalFont)));
            }

            if(invoiceDto.getDamages() > 0) {
                Paragraph damageParagraph = new Paragraph();
                damageParagraph.add(new Phrase("Damages\n", normalFont));
                myTable.addCell(new Cell(damageParagraph));
                myTable.addCell(new Cell(new Phrase("$" + String.valueOf(invoiceDto.getDamages()), normalFont)));
            }

            Paragraph total = new Paragraph("Total Amount: $" + String.valueOf(invoiceDto.getTotal_charge() + invoiceDto.getDamages() + invoiceDto.getLate_fee() + invoiceDto.getInsurance() + (invoiceDto.getBaby_seat()*100)), boldFont);
            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(myTable);
            document.add(total);

            Font statusFont;

            document.add(new Paragraph("Thank you for your business!", boldFont));
            document.close();

            byte[] pdfBytes = out.toByteArray();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simple.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (DocumentException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
