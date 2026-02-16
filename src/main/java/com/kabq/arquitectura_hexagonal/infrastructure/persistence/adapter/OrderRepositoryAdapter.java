package com.kabq.arquitectura_hexagonal.infrastructure.persistence.adapter;

import com.kabq.arquitectura_hexagonal.domain.model.Order;
import com.kabq.arquitectura_hexagonal.domain.port.out.OrderRepositoryPort;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.entity.OrderEntity;
import com.kabq.arquitectura_hexagonal.infrastructure.persistence.repository.JpaOrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final JpaOrderRepository  jpaOrderRepository;

    public OrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Order save(Order order) {
        // Se transforma el order del domain a la entidad para pasarlo a una entidad la cual la bd si puede trabajar
        OrderEntity orderEntity = toEntity(order);
        // Se guarda los datos en la base de datos
        OrderEntity savedOrderEntity = jpaOrderRepository.save(orderEntity);
        // Se retorna el registro al domain
        return toDomain(savedOrderEntity);
    }

    @Override
    public List<Order> findAll() {
        // Mediante jpa hacemos la consulta para retornar los valores
        List<OrderEntity> entities = jpaOrderRepository.findAll();
        // En tiempo de ejecución cada entidad es mapeada hacia el dominio en una lista
        return entities.stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        // Obtiene el registro de la base de datos
        Optional<OrderEntity> entity = jpaOrderRepository.findById(id);
        // Se envía al dominio
        return entity.map(this::toDomain);
    }

    @Override
    public Order update(Order order) {
        // Se transforma el order del domain a la entidad para pasarlo a una entidad la cual la bd si puede trabajar
        OrderEntity orderEntity = toEntity(order);
        // Se guarda los datos en la base de datos
        OrderEntity updatedOrderEntity = jpaOrderRepository.save(orderEntity);
        // Se retorna el registro al domain
        return toDomain(updatedOrderEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaOrderRepository.deleteById(id);
    }

    private OrderEntity toEntity(Order order) {
        if (order.getId() == null) {
            return new OrderEntity(null, order.getCustomerId(), order.getOrderNumber(), order.getDate(), order.getTotalAmount());
        } else {
            return new OrderEntity(order.getId(), order.getCustomerId(), order.getOrderNumber(), order.getDate(), order.getTotalAmount());
        }
    }
    
    private Order toDomain(OrderEntity orderEntity) {
        return new Order(orderEntity.getId(), orderEntity.getCustomerId(), orderEntity.getOrderNumber(), orderEntity.getDate(), orderEntity.getTotalAmount());
    }

}
