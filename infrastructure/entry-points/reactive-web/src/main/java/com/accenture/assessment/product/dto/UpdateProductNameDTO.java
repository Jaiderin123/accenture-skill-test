package com.accenture.assessment.product.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record UpdateProductNameDTO(
        @NotNull(message = "Product is required")
        UUID id,

        @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
        @NotBlank(message = "Product newName is required")
        String name
) {
}

