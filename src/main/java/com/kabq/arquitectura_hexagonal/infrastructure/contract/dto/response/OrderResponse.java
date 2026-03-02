package com.kabq.arquitectura_hexagonal.infrastructure.contract.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String customerName;
    private String orderNumber;
    private String date;
    private BigDecimal totalAmount;
}
