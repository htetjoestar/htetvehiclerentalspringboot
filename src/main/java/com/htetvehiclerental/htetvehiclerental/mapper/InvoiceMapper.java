package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.InvoiceDto;
import com.htetvehiclerental.htetvehiclerental.entity.Invoice;

public class InvoiceMapper {
    public static InvoiceDto mapToInvoiceDto(Invoice invoice){
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setInvoice_id(invoice.getInvoice_id());
        invoiceDto.setDamageDescription(invoice.getDamageDescription());
        invoiceDto.setPaid(invoice.isPaid());
        invoiceDto.setInvoice_created_date(invoice.getInvoice_created_date());

        invoiceDto.setReservation(invoice.getReservation().getReservation_id());
        invoiceDto.setPick_up_date(invoice.getReservation().getPick_up_date());
        invoiceDto.setDrop_off_date(invoice.getReservation().getDrop_off_date());
        invoiceDto.setLate_fee(invoice.getReservation().getLate_fee());
        invoiceDto.setDamages(invoice.getReservation().getDamages());

        invoiceDto.setLicense_plate(invoice.getVehicle().getLicense_plate());
        invoiceDto.setVehicle(invoice.getVehicle().getVehicle_id());
        invoiceDto.setVehicleBrand(invoice.getVehicle().getBrand());
        invoiceDto.setVehicleModel(invoice.getVehicle().getModel());
        invoiceDto.setMake_year(invoice.getVehicle().getMake_year());
        invoiceDto.setImage_url(invoice.getVehicle().getImage_url());

        return invoiceDto;
    } 
    public static Invoice mapToInvoice(InvoiceDto invoiceDto){
        Invoice invoice = new Invoice();
        invoice.setInvoice_id(invoiceDto.getInvoice_id());
        invoice.setDamageDescription(invoiceDto.getDamageDescription());
        invoice.setInvoice_created_date(invoiceDto.getInvoice_created_date());
        invoice.setPaid(invoiceDto.isPaid());

        return invoice;
    }
}
