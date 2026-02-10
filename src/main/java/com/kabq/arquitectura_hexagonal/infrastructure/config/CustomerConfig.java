package com.kabq.arquitectura_hexagonal.infrastructure.config;

import com.kabq.arquitectura_hexagonal.application.service.customer.*;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.*;
import com.kabq.arquitectura_hexagonal.domain.port.out.CustomerRepositoryPort;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.adapter.CustomerRepositoryAdapter;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new CreateCustomerService(customerRepositoryPort);
    }

    @Bean
    public GetCustomerUseCase getCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new GetCustomerService(customerRepositoryPort);
    }

    @Bean
    public GetAllCustomersUseCase getAllCustomersUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new GetAllCustomersService(customerRepositoryPort);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new UpdateCustomerService(customerRepositoryPort);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new DeleteCustomerService(customerRepositoryPort);
    }

    @Bean
    public CustomerRepositoryPort customerRepositoryPort(JpaCustomerRepository jpaCustomerRepository) {
        return new CustomerRepositoryAdapter(jpaCustomerRepository);
    }
}