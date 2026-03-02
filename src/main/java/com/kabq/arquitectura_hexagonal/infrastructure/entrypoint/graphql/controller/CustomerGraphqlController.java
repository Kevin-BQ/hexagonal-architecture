package com.kabq.arquitectura_hexagonal.infrastructure.entrypoint.graphql.controller;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.CreateCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.DeleteCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.GetAllCustomersUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.GetCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.UpdateCustomerUseCase;
import com.kabq.arquitectura_hexagonal.infrastructure.contract.dto.request.CustomerRequest;
import com.kabq.arquitectura_hexagonal.infrastructure.contract.dto.response.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@Validated
public class CustomerGraphqlController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public CustomerGraphqlController(
            CreateCustomerUseCase createCustomerUseCase,
            GetCustomerUseCase getCustomerUseCase,
            GetAllCustomersUseCase getAllCustomersUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    @QueryMapping
    public List<CustomerResponse> customers() {
        return getAllCustomersUseCase.getAllCustomers()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @QueryMapping
    public CustomerResponse customerById(@Argument String id) {
        Long customerId = parseId(id, "customer id");
        return getCustomerUseCase.getCustomerById(customerId)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));
    }

    @MutationMapping
    public CustomerResponse createCustomer(@Argument @Valid CustomerRequest input) {
        Customer customer = new Customer(null, input.getName());
        return toResponse(createCustomerUseCase.createCustomer(customer));
    }

    @MutationMapping
    public CustomerResponse updateCustomer(@Argument String id, @Argument @Valid CustomerRequest input) {
        Long customerId = parseId(id, "customer id");
        Customer customer = new Customer(customerId, input.getName());
        return toResponse(updateCustomerUseCase.updateCustomer(customerId, customer));
    }

    @MutationMapping
    public Boolean deleteCustomer(@Argument String id) {
        Long customerId = parseId(id, "customer id");
        deleteCustomerUseCase.deleteCustomer(customerId);
        return Boolean.TRUE;
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName());
    }

    private Long parseId(String id, String fieldName) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Formato inválido para " + fieldName);
        }
    }
}
