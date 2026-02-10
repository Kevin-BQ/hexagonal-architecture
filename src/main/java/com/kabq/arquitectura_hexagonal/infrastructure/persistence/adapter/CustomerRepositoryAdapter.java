package com.kabq.arquitectura_hexagonal.infrastructure.persistence.adapter;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.port.out.CustomerRepositoryPort;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.entity.CustomerEntity;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final JpaCustomerRepository jpaCustomerRepository;

    public CustomerRepositoryAdapter(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        CustomerEntity savedEntity = jpaCustomerRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Optional<CustomerEntity> entity = jpaCustomerRepository.findById(id);
        return entity.map(this::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        List<CustomerEntity> entities = jpaCustomerRepository.findAll();
        return entities.stream().map(this::toDomain).toList();
    }

    @Override
    public Customer update(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        CustomerEntity updatedEntity = jpaCustomerRepository.save(entity);
        return toDomain(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }

    private CustomerEntity toEntity(Customer customer) {
        if (customer.getId() == null) {
            return new CustomerEntity(null, customer.getName());
        } else {
            return new CustomerEntity(customer.getId(), customer.getName());
        }
    }

    private Customer toDomain(CustomerEntity entity) {
        return new Customer(entity.getId(), entity.getName());
    }
}