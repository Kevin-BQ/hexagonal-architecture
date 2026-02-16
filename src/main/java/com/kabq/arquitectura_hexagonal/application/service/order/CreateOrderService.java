package com.kabq.arquitectura_hexagonal.application.service.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.CreateOrderUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.OrderRepositoryPort;

public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public CreateOrderService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepositoryPort.save(order);
    }
}
