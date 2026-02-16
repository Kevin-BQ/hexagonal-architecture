package com.kabq.arquitectura_hexagonal.domain.port.in.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;

public interface CreateOrderUseCase {
    Order createOrder(Order order);
}
