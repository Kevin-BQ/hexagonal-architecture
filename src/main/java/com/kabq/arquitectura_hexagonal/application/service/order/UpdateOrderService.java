package com.kabq.arquitectura_hexagonal.application.service.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.UpdateOrderUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.OrderRepositoryPort;

public class UpdateOrderService implements UpdateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public UpdateOrderService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order orderToUpdate = new Order(id, order.getCustomerId(), order.getOrderNumber(), order.getDate(), order.getTotalAmount());
        return orderRepositoryPort.update(orderToUpdate);
    }
}
