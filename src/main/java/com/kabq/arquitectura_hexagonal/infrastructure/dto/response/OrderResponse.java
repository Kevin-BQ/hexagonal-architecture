package com.kabq.arquitectura_hexagonal.infrastructure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String customerName;
    private String orderNumber;
    private LocalDateTime date;
    private BigDecimal totalAmount;
}
