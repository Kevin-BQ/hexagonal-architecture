package com.kabq.arquitectura_hexagonal.domain.port.in.customer;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import java.util.List;

public interface GetAllCustomersUseCase {
    List<Customer> getAllCustomers();
}