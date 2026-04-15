package com.accenture.assessment.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateFranchiseNameDTO(
        @NotNull(message = "Id is required")
        UUID id,

        @NotBlank(message = "Branch new name is required")
        @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
        String newName
) {
}

