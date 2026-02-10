package com.kabq.arquitectura_hexagonal.application.service.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.GetCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.CustomerRepositoryPort;

import java.util.Optional;

public class GetCustomerService implements GetCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public GetCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepositoryPort.findById(id);
    }
}