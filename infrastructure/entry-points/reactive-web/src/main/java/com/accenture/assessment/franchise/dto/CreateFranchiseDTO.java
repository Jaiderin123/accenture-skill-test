package com.accenture.assessment.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFranchiseDTO(
        @NotBlank(message = "Branch newName is required")
        @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
        String name
) {
}
