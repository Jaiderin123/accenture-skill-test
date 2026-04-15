package com.accenture.assessment.product.usecase;

import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.service.ProductServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductServiceDomain productServiceDomain;
    private final ProductRepository productRepository;

    @Override
    public Mono<Void> delete(UUID id) {
        return productServiceDomain.assertIdExists(id)
                .then(Mono.defer(() ->productRepository.delete(id)));
    }
}
