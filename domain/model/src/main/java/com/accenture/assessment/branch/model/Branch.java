package com.accenture.assessment.branch.model;

import com.accenture.assessment.product.model.Product;

import java.util.List;
import java.util.UUID;

public record Branch(
        UUID id,
        UUID franchiseId,
        String name,
        List<Product> products
) {}
