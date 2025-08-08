package com.htetvehiclerental.htetvehiclerental.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.htetvehiclerental.htetvehiclerental.dto.InvoiceDto;
import com.htetvehiclerental.htetvehiclerental.dto.MaintenanceDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.Invoice;
import com.htetvehiclerental.htetvehiclerental.entity.Maintenance;
import com.htetvehiclerental.htetvehiclerental.entity.Reservation;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;
import com.htetvehiclerental.htetvehiclerental.exception.ResourceNotFoundException;
import com.htetvehiclerental.htetvehiclerental.mapper.InvoiceMapper;
import com.htetvehiclerental.htetvehiclerental.mapper.MaintenanceMapper;
import com.htetvehiclerental.htetvehiclerental.mapper.ReservationMapper;
import com.htetvehiclerental.htetvehiclerental.repository.InvoiceRepository;
import com.htetvehiclerental.htetvehiclerental.repository.ReservationRepository;
import com.htetvehiclerental.htetvehiclerental.repository.VehicleRepository;
import com.htetvehiclerental.htetvehiclerental.service.InvoiceService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{
    private InvoiceRepository invoiceRepository;
    private VehicleRepository vehicleRepository;
    private ReservationRepository reservationRepository;
    
    @Override
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = InvoiceMapper.mapToInvoice(invoiceDto);
        Reservation reservation = reservationRepository.findById(invoiceDto.getReservation()).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle does not exist with given id: " + invoiceDto.getReservation()));
        invoice.setReservation(reservation);
        Vehicle vehicle = vehicleRepository.findById(invoiceDto.getVehicle()).orElseThrow(() ->
                new ResourceNotFoundException("Vehicle does not exist with given id: " + invoiceDto.getVehicle()));
        invoice.setVehicle(vehicle);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return InvoiceMapper.mapToInvoiceDto(savedInvoice);
    }
    @Override
    public InvoiceDto getInvoiceByID(Long invoice_id) {
        Invoice invoice = invoiceRepository.findById(invoice_id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoice_id));
        return InvoiceMapper.mapToInvoiceDto(invoice);
    }    

    @Override
    public InvoiceDto getInvoiceDtoByReservationId(Long reservation_id) {
        Invoice invoice = invoiceRepository.findByReservationId(reservation_id);
        if (invoice == null) {
            throw new ResourceNotFoundException("Invoice not found for reservation id: " + reservation_id);
        }
        return InvoiceMapper.mapToInvoiceDto(invoice);
    }
    
    @Override
    public List<InvoiceDto> findByCustomerId(Long customerId, boolean paid) {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId, paid);
        return invoices.stream()
                .map((invoice) -> InvoiceMapper.mapToInvoiceDto(invoice))
                .collect(Collectors.toList());
    }
    @Override
    public boolean existsByCustomerIdAndUnpaid(Long customerId) {
        return invoiceRepository.existsUnpaidByCustomerId(customerId);
    }
    @Override
    public Invoice getInvoiceByReservationId(Long reservation_id) {
        Invoice invoice = invoiceRepository.findByReservationId(reservation_id);
        if (invoice == null) {
            throw new ResourceNotFoundException("Invoice not found for reservation id: " + reservation_id);
        }
        return invoice;
    }
    @Override
    public InvoiceDto updateInvoice(Long maintenanceId, InvoiceDto invoiceDto) {
        Invoice invoice = invoiceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + maintenanceId));
        invoice.setPaid(invoiceDto.isPaid());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return InvoiceMapper.mapToInvoiceDto(savedInvoice);
    }
}
