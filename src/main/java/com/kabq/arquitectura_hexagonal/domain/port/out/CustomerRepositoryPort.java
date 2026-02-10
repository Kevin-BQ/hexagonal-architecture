package com.kabq.arquitectura_hexagonal.domain.port.out;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    Customer update(Customer customer);
    void deleteById(Long id);
}