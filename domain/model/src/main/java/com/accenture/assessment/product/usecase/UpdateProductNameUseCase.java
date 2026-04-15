package com.accenture.assessment.product.usecase;

import com.accenture.assessment.product.model.Product;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UpdateProductNameUseCase {
    Mono<Product> updateName(UUID id, String name);
}
