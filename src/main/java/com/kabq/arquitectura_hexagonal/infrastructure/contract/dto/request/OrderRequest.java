package com.kabq.arquitectura_hexagonal.infrastructure.contract.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderRequest {

    @NotNull(message = "El ID del cliente no puede estar vacío")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Long customerId;

    @NotBlank(message = "El número de orden no puede estar vacío")
    @Size(min = 3, max = 50, message = "El número de orden debe tener entre 3 y 50 caracteres")
    private String orderNumber;

    @NotBlank(message = "La fecha no puede estar vacía")
    private String date;

    @NotNull(message = "El monto total no puede estar vacío")
    @Positive(message = "El monto total debe ser un número positivo")
    private BigDecimal totalAmount;
}
