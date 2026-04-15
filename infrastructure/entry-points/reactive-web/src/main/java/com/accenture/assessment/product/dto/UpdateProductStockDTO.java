package com.accenture.assessment.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateProductStockDTO(
        @NotNull(message = "Product is required")
        UUID id,

        @NotNull(message = "Product stock is required")
        @Min(value = 0, message = "The product stock cannot be negative")
        Integer stock
) {
}

