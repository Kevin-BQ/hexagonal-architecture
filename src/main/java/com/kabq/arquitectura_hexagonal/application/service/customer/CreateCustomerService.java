package com.kabq.arquitectura_hexagonal.application.service.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.CreateCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.CustomerRepositoryPort;

public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CreateCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepositoryPort.save(customer);
    }
}