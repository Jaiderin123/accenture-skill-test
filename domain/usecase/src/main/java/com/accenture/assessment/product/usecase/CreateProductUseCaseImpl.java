package com.accenture.assessment.product.usecase;

import com.accenture.assessment.branch.service.BranchServiceDomain;
import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.model.Product;
import com.accenture.assessment.product.service.ProductServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final BranchServiceDomain branchServiceDomain;
    private final ProductServiceDomain productServiceDomain;
    private final ProductRepository productRepository;

    @Override
    public Mono<Product> create(UUID branchId, String name, Integer stock) {
        return branchServiceDomain.assertIdExists(branchId)
                .then(Mono.defer(() ->
                                productServiceDomain.assertNameNotExists(name)
                                        .then(Mono.defer(() ->
                                                        productRepository.create(branchId, name, stock)
                                                )
                                        )
                        )
                );
    }
}
