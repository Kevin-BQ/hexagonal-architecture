package com.kabq.arquitectura_hexagonal.application.service.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.UpdateCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.CustomerRepositoryPort;

public class UpdateCustomerService implements UpdateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public UpdateCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer customerToUpdate = new Customer(id, customer.getName());
        return customerRepositoryPort.update(customerToUpdate);
    }
}
