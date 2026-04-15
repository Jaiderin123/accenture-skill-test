package com.accenture.assessment.product.usecase;

import com.accenture.assessment.product.model.Product;
import com.accenture.assessment.product.service.ProductServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateProductNameUseCaseImpl implements UpdateProductNameUseCase {
    private final ProductServiceDomain productServiceDomain;

    @Override
    public Mono<Product> updateName(UUID id, String name) {
        return productServiceDomain.validateNewNameToChange(id, name)
                .then(Mono.defer(() -> productServiceDomain.updateName(id,name)));
    }
}
