package com.kabq.arquitectura_hexagonal.domain.port.in.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import java.util.Optional;

public interface GetCustomerUseCase {
    Optional<Customer> getCustomerById(Long id);
}