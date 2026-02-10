package com.kabq.arquitectura_hexagonal.infrastructure.controller;

import com.kabq.arquitectura_hexagonal.domain.port.in.customer.*;
import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.infrastructure.dto.request.CustomerRequest;
import com.kabq.arquitectura_hexagonal.infrastructure.dto.response.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase,
                         GetCustomerUseCase getCustomerUseCase,
                         GetAllCustomersUseCase getAllCustomersUseCase,
                         UpdateCustomerUseCase updateCustomerUseCase,
                         DeleteCustomerUseCase deleteCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        Customer customer = new Customer(null, request.getName());
        Customer savedCustomer = createCustomerUseCase.createCustomer(customer);
        CustomerResponse response = new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getName()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = getCustomerUseCase.getCustomerById(id);
        if (customer.isPresent()) {
            CustomerResponse response = new CustomerResponse(
                    customer.get().getId(),
                    customer.get().getName()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers = getAllCustomersUseCase.getAllCustomers();
        List<CustomerResponse> responseList = customers.stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getName()
                ))
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                   @Valid @RequestBody CustomerRequest request) {
        Customer customerToUpdate = new Customer(id, request.getName());
        Customer updatedCustomer = updateCustomerUseCase.updateCustomer(id, customerToUpdate);
        CustomerResponse response = new CustomerResponse(
                updatedCustomer.getId(),
                updatedCustomer.getName()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        deleteCustomerUseCase.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}