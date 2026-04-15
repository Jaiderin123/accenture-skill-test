package com.accenture.assessment.product.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CreateProductDTO(
        @NotNull(message = "Branch is required")
        UUID branchId,

        @NotBlank(message = "Product newName is required")
        @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
        String name,

        @NotNull(message = "Product stock is required")
        @Min(value = 0, message = "The product stock cannot be negative")
        Integer stock
) {
}
