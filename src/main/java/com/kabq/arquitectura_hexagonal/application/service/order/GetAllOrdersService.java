package com.kabq.arquitectura_hexagonal.application.service.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.GetAllOrdersUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.OrderRepositoryPort;

import java.util.List;

public class GetAllOrdersService implements GetAllOrdersUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public GetAllOrdersService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepositoryPort.findAll();
    }
}
