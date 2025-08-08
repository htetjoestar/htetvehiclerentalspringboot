package com.htetvehiclerental.htetvehiclerental.service;

import java.io.IOException;
import java.util.List;

import com.htetvehiclerental.htetvehiclerental.dto.InvoiceDto;
import com.htetvehiclerental.htetvehiclerental.dto.ReservationDto;
import com.htetvehiclerental.htetvehiclerental.entity.Invoice;

public interface InvoiceService {
    InvoiceDto createInvoice(InvoiceDto invoiceDto) throws IOException;
    InvoiceDto getInvoiceByID(Long invoice_id);
    InvoiceDto getInvoiceDtoByReservationId(Long reservation_id);
    List<InvoiceDto> findByCustomerId(Long customerId, boolean paid);
    Invoice getInvoiceByReservationId(Long reservation_id);
    boolean existsByCustomerIdAndUnpaid(Long customerId);
    InvoiceDto updateInvoice(Long invoiceID, InvoiceDto updatedInvoice);
}
