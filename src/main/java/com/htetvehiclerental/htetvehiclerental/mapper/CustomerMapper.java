package com.htetvehiclerental.htetvehiclerental.mapper;

import com.htetvehiclerental.htetvehiclerental.dto.CustomerDto;
import com.htetvehiclerental.htetvehiclerental.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer_id(customer.getCustomer_id());
        customerDto.setCust_first_name(customer.getCust_first_name());
        customerDto.setCust_last_name(customer.getCust_last_name());
        customerDto.setCust_email(customer.getCust_email());
        customerDto.setCust_phone_number(customer.getCust_phone_number());
        customerDto.setCust_license_number(customer.getCust_license_number());
        customerDto.setCust_date_of_birth(customer.getCust_date_of_birth());
        customerDto.setCust_license_expiry_date(customer.getCust_license_expiry_date());
        customerDto.setCust_emergency_contact_name(customer.getCust_emergency_contact_name());
        customerDto.setCust_emergency_contact_number(customer.getCust_emergency_contact_number());
        customerDto.setCust_password(customer.getCust_password());
        customerDto.setCust_id_doc(customer.getCust_id_doc());
        customerDto.setCust_created_date(customer.getCust_created_date());
        customerDto.setCust_modified_date(customer.getCust_modified_date());
        customerDto.setCust_last_action(customer.getCust_last_action());
        customerDto.setCust_active(customer.isCust_active());
        customerDto.setCust_id_img(customer.getCust_id_img());

        // Map other fields as necessary
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCustomer_id(customerDto.getCustomer_id());
        customer.setCust_first_name(customerDto.getCust_first_name());
        customer.setCust_last_name(customerDto.getCust_last_name());
        customer.setCust_email(customerDto.getCust_email());
        customer.setCust_phone_number(customerDto.getCust_phone_number());
        customer.setCust_license_number(customerDto.getCust_license_number());
        customer.setCust_date_of_birth(customerDto.getCust_date_of_birth());
        customer.setCust_license_expiry_date(customerDto.getCust_license_expiry_date());
        customer.setCust_emergency_contact_name(customerDto.getCust_emergency_contact_name());
        customer.setCust_emergency_contact_number(customerDto.getCust_emergency_contact_number());
        customer.setCust_password(customerDto.getCust_password());
        customer.setCust_id_doc(customerDto.getCust_id_doc());
        customer.setCust_created_date(customerDto.getCust_created_date());
        customer.setCust_modified_date(customerDto.getCust_modified_date());
        customer.setCust_last_action(customerDto.getCust_last_action());
        customer.setCust_active(customerDto.isCust_active());
        customer.setCust_id_img(customerDto.getCust_id_img());

        // Map other fields as necessary
        return customer;
    }
}
