package com.kabq.arquitectura_hexagonal.infrastructure.controller;

import com.kabq.arquitectura_hexagonal.domain.port.in.order.*;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.GetCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.model.Order;
import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.infrastructure.dto.request.OrderRequest;
import com.kabq.arquitectura_hexagonal.infrastructure.dto.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final GetCustomerUseCase getCustomerUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           GetOrderUseCase getOrderUseCase,
                           GetAllOrdersUseCase getAllOrdersUseCase,
                           UpdateOrderUseCase updateOrderUseCase,
                           DeleteOrderUseCase deleteOrderUseCase,
                           GetCustomerUseCase getCustomerUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = new Order(null, request.getCustomerId(), request.getOrderNumber(), 
                                request.getDate(), request.getTotalAmount());
        Order savedOrder = createOrderUseCase.createOrder(order);
        
        // Obtener el nombre del cliente
        String customerName = getCustomerUseCase.getCustomerById(savedOrder.getCustomerId())
                .map(Customer::getName)
                .orElse("Cliente no encontrado");
        
        OrderResponse response = new OrderResponse(
                savedOrder.getId(),
                customerName,
                savedOrder.getOrderNumber(),
                savedOrder.getDate(),
                savedOrder.getTotalAmount()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Optional<Order> order = getOrderUseCase.getOrderById(id);
        if (order.isPresent()) {
            // Obtener el nombre del cliente
            String customerName = getCustomerUseCase.getCustomerById(order.get().getCustomerId())
                    .map(Customer::getName)
                    .orElse("Cliente no encontrado");
            
            OrderResponse response = new OrderResponse(
                    order.get().getId(),
                    customerName,
                    order.get().getOrderNumber(),
                    order.get().getDate(),
                    order.get().getTotalAmount()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = getAllOrdersUseCase.getAllOrders();
        List<OrderResponse> responseList = orders.stream()
                .map(order -> {
                    // Obtener el nombre del cliente para cada orden
                    String customerName = getCustomerUseCase.getCustomerById(order.getCustomerId())
                            .map(Customer::getName)
                            .orElse("Cliente no encontrado");
                    
                    return new OrderResponse(
                            order.getId(),
                            customerName,
                            order.getOrderNumber(),
                            order.getDate(),
                            order.getTotalAmount()
                    );
                })
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id,
                                                      @Valid @RequestBody OrderRequest request) {
        Order orderToUpdate = new Order(id, request.getCustomerId(), request.getOrderNumber(),
                                        request.getDate(), request.getTotalAmount());
        Order updatedOrder = updateOrderUseCase.updateOrder(id, orderToUpdate);
        
        // Obtener el nombre del cliente
        String customerName = getCustomerUseCase.getCustomerById(updatedOrder.getCustomerId())
                .map(Customer::getName)
                .orElse("Cliente no encontrado");
        
        OrderResponse response = new OrderResponse(
                updatedOrder.getId(),
                customerName,
                updatedOrder.getOrderNumber(),
                updatedOrder.getDate(),
                updatedOrder.getTotalAmount()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        deleteOrderUseCase.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
