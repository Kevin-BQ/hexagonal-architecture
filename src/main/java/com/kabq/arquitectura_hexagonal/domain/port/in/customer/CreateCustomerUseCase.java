package com.kabq.arquitectura_hexagonal.domain.port.in.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer createCustomer(Customer customer);
}