package com.kabq.arquitectura_hexagonal.infrastructure.config;

import com.kabq.arquitectura_hexagonal.application.service.order.*;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.*;
import com.kabq.arquitectura_hexagonal.domain.port.out.OrderRepositoryPort;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.adapter.OrderRepositoryAdapter;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.repository.JpaOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    /*
    *  Se brinda el comportamiento que está en el servicio que es la implementación de caso de uso y
    * el caso de uso recibe los datos que los obtiene desde el puerto
    */
    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new CreateOrderService(orderRepositoryPort);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new GetOrderService(orderRepositoryPort);
    }

    @Bean
    public GetAllOrdersUseCase getAllOrdersUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new GetAllOrdersService(orderRepositoryPort);
    }

    @Bean
    public UpdateOrderUseCase updateOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new UpdateOrderService(orderRepositoryPort);
    }

    @Bean
    public DeleteOrderUseCase deleteOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new DeleteOrderService(orderRepositoryPort);
    }

    /*
     *  Se brinda el comportamiento que está en el adaptador al Puerto
     * Brindando la forma en la cual se guardaran los datos en este caso mediante JPA
     */
    @Bean
    public OrderRepositoryPort orderRepositoryPort(JpaOrderRepository jpaOrderRepository) {
        return new OrderRepositoryAdapter(jpaOrderRepository);
    }
}
