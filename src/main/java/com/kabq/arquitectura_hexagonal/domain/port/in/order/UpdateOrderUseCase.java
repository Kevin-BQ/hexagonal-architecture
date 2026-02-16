package com.kabq.arquitectura_hexagonal.domain.port.in.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;

public interface UpdateOrderUseCase {
    Order updateOrder(Long id, Order order);
}
