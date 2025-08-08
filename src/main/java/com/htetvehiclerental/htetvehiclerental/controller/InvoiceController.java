package com.htetvehiclerental.htetvehiclerental.controller;

import com.htetvehiclerental.htetvehiclerental.dto.InvoiceDto;
import com.htetvehiclerental.htetvehiclerental.dto.MaintenanceDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.service.InvoiceService;
import com.htetvehiclerental.htetvehiclerental.entity.Invoice;
import lombok.AllArgsConstructor;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;

import com.htetvehiclerental.htetvehiclerental.CustomUtils;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceDto invoiceDto) {
        try {
            InvoiceDto createdInvoice = invoiceService.createInvoice(invoiceDto);
            return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    @PutMapping("{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable("id") Long invoiceID,
                                                    @RequestBody InvoiceDto updatedInvoice){
        InvoiceDto invoiceDto = invoiceService.updateInvoice(invoiceID, updatedInvoice);
        return ResponseEntity.ok(invoiceDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceByID(@PathVariable("id") Long invoice_id) {
        try {
            InvoiceDto invoiceDto = invoiceService.getInvoiceByID(invoice_id);
            if (invoiceDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(invoiceDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    } 

    @GetMapping("/reservation/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceByReservationID(@PathVariable("id") Long reservation_id) {
        try {
            InvoiceDto invoiceDto = invoiceService.getInvoiceDtoByReservationId(reservation_id);
            if (invoiceDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(invoiceDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }        
    }   
@GetMapping("/customer/{customerId}/paid/{paid}")
public List<InvoiceDto> getInvoicesByCustomerId(@PathVariable Long customerId,
                                                @PathVariable boolean paid){
        return invoiceService.findByCustomerId(customerId, paid);
    }

@GetMapping("/has-unpaid")
public ResponseEntity<Boolean> hasUnpaidInvoices(@RequestParam Long customerId) {
    boolean hasUnpaid = invoiceService.existsByCustomerIdAndUnpaid(customerId);
    return ResponseEntity.ok(hasUnpaid);
}

    @GetMapping("/pdf/reservation/{id}")
    public ResponseEntity<byte[]> generateSimplePdf(@PathVariable("id") Long reservation_id) {
        Invoice invoiceDto = invoiceService.getInvoiceByReservationId(reservation_id); // Example reservation ID, replace as needed

        Integer diffDays = CustomUtils.getDifferenceInDaysInclusive(invoiceDto.getReservation().getDrop_off_date(), invoiceDto.getReservation().getActual_drop_off_date());
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            // Create PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.addTitle("Invoice");
            document.add(new Paragraph("Date: " + CustomUtils.formatLocalDate(invoiceDto.getInvoice_created_date().toLocalDate()), boldFont));
            document.add(new Paragraph("Bill To: " + invoiceDto.getReservation().getCustomer().getCust_first_name() + " " + invoiceDto.getReservation().getCustomer().getCust_last_name(), boldFont));
            document.add(new Paragraph("             " + invoiceDto.getReservation().getCustomer().getCust_email(), boldFont));
            document.add(new Paragraph("From: Htet Vehicle Rentals", boldFont));
            document.add(new Paragraph("Invoice for extra charges.", boldFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Dear Customer,"));
            document.add(new Paragraph("Thank you for choosing Htet Vehicle Rentals."));
            document.add(new Paragraph("Unfortunately, You have extra charges on your rental from " + CustomUtils.formatLocalDate(invoiceDto.getReservation().getPick_up_date()) + " to " + CustomUtils.formatLocalDate(invoiceDto.getReservation().getDrop_off_date())));
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
        });

            if(invoiceDto.getReservation().getLate_fee() > 0) {
                Paragraph lateFeeParagraph = new Paragraph();
                lateFeeParagraph.add(new Phrase("Late Fee\n", normalFont));
                lateFeeParagraph.add(new Phrase(diffDays + " days late", normalFont));
                myTable.addCell(new Cell(lateFeeParagraph));
                myTable.addCell(new Cell(new Phrase("$" + String.valueOf(invoiceDto.getReservation().getLate_fee()), normalFont)));
            }
            if(invoiceDto.getReservation().getDamages() > 0) {
                Paragraph damageParagraph = new Paragraph();
                damageParagraph.add(new Phrase("Damages\n", normalFont));
                damageParagraph.add(new Phrase(invoiceDto.getDamageDescription(), normalFont));
                myTable.addCell(new Cell(damageParagraph));
                myTable.addCell(new Cell(new Phrase("$" + String.valueOf(invoiceDto.getReservation().getDamages()), normalFont)));
            }

            Paragraph total = new Paragraph("Total Amount: $" + String.valueOf(invoiceDto.getReservation().getDamages() + invoiceDto.getReservation().getLate_fee()), boldFont);
            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(myTable);
            document.add(total);

            Font statusFont;
if (invoiceDto.isPaid()) {
    statusFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.GREEN);
    Paragraph paid = new Paragraph("PAID", statusFont);
    paid.setAlignment(Element.ALIGN_RIGHT);
    document.add(paid);
} else {
    statusFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.RED);
    Paragraph unpaid = new Paragraph("UNPAID", statusFont);
    unpaid.setAlignment(Element.ALIGN_RIGHT);
    document.add(unpaid);
}

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
