package com.kabq.arquitectura_hexagonal.domain.port.out;

import com.kabq.arquitectura_hexagonal.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order update(Order order);
    void deleteById(Long id);
}
