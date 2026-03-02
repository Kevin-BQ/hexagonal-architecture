package com.kabq.arquitectura_hexagonal.infrastructure.entrypoint.graphql.controller;

import com.kabq.arquitectura_hexagonal.domain.model.Customer;
import com.kabq.arquitectura_hexagonal.domain.model.Order;
import com.kabq.arquitectura_hexagonal.domain.port.in.customer.GetCustomerUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.CreateOrderUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.DeleteOrderUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.GetAllOrdersUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.GetOrderUseCase;
import com.kabq.arquitectura_hexagonal.domain.port.in.order.UpdateOrderUseCase;
import com.kabq.arquitectura_hexagonal.infrastructure.contract.dto.request.OrderRequest;
import com.kabq.arquitectura_hexagonal.infrastructure.contract.dto.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@Validated
public class OrderGraphqlController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final GetCustomerUseCase getCustomerUseCase;

    public OrderGraphqlController(
            CreateOrderUseCase createOrderUseCase,
            GetOrderUseCase getOrderUseCase,
            GetAllOrdersUseCase getAllOrdersUseCase,
            UpdateOrderUseCase updateOrderUseCase,
            DeleteOrderUseCase deleteOrderUseCase,
            GetCustomerUseCase getCustomerUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
    }

    @QueryMapping
    public List<OrderResponse> orders() {
        return getAllOrdersUseCase.getAllOrders()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @QueryMapping
    public OrderResponse orderById(@Argument String id) {
        Long orderId = parseId(id, "order id");
        return getOrderUseCase.getOrderById(orderId)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Orden no encontrada"));
    }

    @MutationMapping
    public OrderResponse createOrder(@Argument @Valid OrderRequest input) {
        Customer customer = getCustomerUseCase.getCustomerById(input.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("El cliente no existe"));

        Order order = new Order(
                null,
                customer.getId(),
                input.getOrderNumber(),
                parseDate(input.getDate()),
                input.getTotalAmount()
        );

        return toResponse(createOrderUseCase.createOrder(order));
    }

    @MutationMapping
    public OrderResponse updateOrder(@Argument String id, @Argument @Valid OrderRequest input) {
        Long orderId = parseId(id, "order id");
        Order order = toOrder(orderId, input);
        return toResponse(updateOrderUseCase.updateOrder(orderId, order));
    }

    @MutationMapping
    public Boolean deleteOrder(@Argument String id) {
        Long orderId = parseId(id, "order id");
        deleteOrderUseCase.deleteOrder(orderId);
        return Boolean.TRUE;
    }

    private Order toOrder(Long id, OrderRequest input) {
        return new Order(
                id,
                input.getCustomerId(),
                input.getOrderNumber(),
                parseDate(input.getDate()),
                input.getTotalAmount()
        );
    }

    private OrderResponse toResponse(Order order) {
        String customerName = getCustomerUseCase.getCustomerById(order.getCustomerId())
                .map(Customer::getName)
                .orElse("Cliente no encontrado");

        return new OrderResponse(
                order.getId(),
                customerName,
                order.getOrderNumber(),
                order.getDate().toString(),
                order.getTotalAmount()
        );
    }

    private Long parseId(String id, String fieldName) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Formato inválido para " + fieldName);
        }
    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DateTimeFormatter spaceFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (DateTimeFormatter formatter : List.of(isoFormatter, spaceFormatter, minuteFormatter)) {
            try {
                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException ignored) {
                // Probamos el siguiente formato soportado
            }
        }

        throw new IllegalArgumentException(
                "Formato inválido para date. Usa: 2026-03-02T10:30:00 o 2026-03-02 10:30:00"
        );
    }
}
