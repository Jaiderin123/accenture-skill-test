package com.accenture.assessment.product.usecase;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DeleteProductUseCase {
    Mono<Void> delete(UUID id);
}
