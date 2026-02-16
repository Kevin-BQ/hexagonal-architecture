package com.kabq.arquitectura_hexagonal.infrastructure.persistence.repository;

import com.kabq.arquitectura_hexagonal.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}
