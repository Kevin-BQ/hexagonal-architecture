package com.kabq.arquitectura_hexagonal.application.service.order;

import com.kabq.arquitectura_hexagonal.domain.port.in.order.DeleteOrderUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.out.OrderRepositoryPort;

public class DeleteOrderService implements DeleteOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public DeleteOrderService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepositoryPort.deleteById(id);
    }
}
