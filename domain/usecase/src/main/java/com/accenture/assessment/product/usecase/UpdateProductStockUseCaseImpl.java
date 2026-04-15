package com.accenture.assessment.product.usecase;

import com.accenture.assessment.product.model.Product;
import com.accenture.assessment.product.service.ProductServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateProductStockUseCaseImpl implements UpdateProductStockUseCase {
    private final ProductServiceDomain productServiceDomain;

    @Override
    public Mono<Product> updateStock(UUID id, Integer stock) {
        return productServiceDomain.assertIdExists(id)
                .then(Mono.defer(() -> productServiceDomain.updateStock(id,stock)));
    }
}
