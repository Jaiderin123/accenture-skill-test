package com.accenture.assessment.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateBranchDTO(
        @NotNull(message = "Franchise newName is required")
        UUID franchiseId,

        @NotBlank(message = "Branch newName is required")
        @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
        String name
) {
}
