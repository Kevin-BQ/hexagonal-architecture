package com.kabq.arquitectura_hexagonal.application.service.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.DeleteCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.CustomerRepositoryPort;

public class DeleteCustomerService implements DeleteCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public DeleteCustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepositoryPort.deleteById(id);
    }
}