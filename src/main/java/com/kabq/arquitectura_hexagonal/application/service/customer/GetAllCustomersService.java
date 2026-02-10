package com.kabq.arquitectura_hexagonal.application.service.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.GetAllCustomersUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.CustomerRepositoryPort;

import java.util.List;

public class GetAllCustomersService implements GetAllCustomersUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public GetAllCustomersService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepositoryPort.findAll();
    }
}