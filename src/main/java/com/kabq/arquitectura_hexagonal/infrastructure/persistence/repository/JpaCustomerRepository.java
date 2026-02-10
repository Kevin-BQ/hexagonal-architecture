package com.kabq.arquitectura_hexagonal.infrastructure.persistence.repository;

import com.kabq.arquitectura_hexagonal.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
}