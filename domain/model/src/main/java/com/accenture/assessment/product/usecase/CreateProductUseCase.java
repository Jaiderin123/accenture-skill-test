package com.accenture.assessment.product.usecase;

import com.accenture.assessment.product.model.Product;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CreateProductUseCase {
    Mono<Product> create(UUID branchId, String name, Integer stock);
}
