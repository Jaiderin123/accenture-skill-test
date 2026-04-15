package com.accenture.assessment.product.model;

import java.util.UUID;

public record Product(
        UUID id,
        UUID branchId,
        String name,
        Integer stock
) {}
