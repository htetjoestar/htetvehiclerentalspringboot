package com.htetvehiclerental.htetvehiclerental.service;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.htetvehiclerental.htetvehiclerental.repository.ReservationRepository;
import com.htetvehiclerental.htetvehiclerental.repository.InvoiceRepository;
import com.htetvehiclerental.htetvehiclerental.dto.NotificationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Invoice;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;

import com.htetvehiclerental.htetvehiclerental.CustomUtils;

@Service
public class NotificationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<NotificationDto> getCustomerNotifications(Long customerId) {
        List<NotificationDto> notifications = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Reservation Notifications
        List<Reservation> reservationsToday = reservationRepository
            .findReservationsForToday(customerId, today);
        for (Reservation res : reservationsToday) {
            String message = res.getVehicle().getLicense_plate() + " "
             + res.getVehicle().getBrand() + " " 
              + res.getVehicle().getModel() + " " 
               + res.getVehicle().getMake_year() + " "  + " is scheduled for today.";
            notifications.add(new NotificationDto(res.getReservation_id(), message, "RESERVATION"));
        }

        // Unpaid Invoice Notifications
        List<Invoice> unpaidInvoices = invoiceRepository
            .findUnpaidInvoicesByCustomerId(customerId);
            
        for (Invoice inv : unpaidInvoices) {
            String formattedDate = CustomUtils.formatLocalDateTime(inv.getInvoice_created_date());
            String message = "New Invoice from " + formattedDate + " is unpaid.";
            notifications.add(new NotificationDto(inv.getReservation().getReservation_id(), message, "INVOICE"));
        }

        return notifications;
    }
}