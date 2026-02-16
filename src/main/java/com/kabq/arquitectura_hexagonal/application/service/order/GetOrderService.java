package com.kabq.arquitectura_hexagonal.application.service.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.GetOrderUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.OrderRepositoryPort;

import java.util.Optional;

public class GetOrderService implements GetOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public GetOrderService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepositoryPort.findById(id);
    }
}
