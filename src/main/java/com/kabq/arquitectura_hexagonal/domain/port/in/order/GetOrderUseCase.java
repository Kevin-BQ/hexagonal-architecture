package com.kabq.arquitectura_hexagonal.domain.port.in.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;
import java.util.Optional;

public interface GetOrderUseCase {
    Optional<Order> getOrderById(Long id);
}
