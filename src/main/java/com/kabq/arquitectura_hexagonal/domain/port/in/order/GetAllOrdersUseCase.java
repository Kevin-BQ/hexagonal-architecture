package com.kabq.arquitectura_hexagonal.domain.port.in.order;

import com.kabq.arquitectura_hexagonal.domain.model.Order;
import java.util.List;

public interface GetAllOrdersUseCase {
    List<Order> getAllOrders();
}
