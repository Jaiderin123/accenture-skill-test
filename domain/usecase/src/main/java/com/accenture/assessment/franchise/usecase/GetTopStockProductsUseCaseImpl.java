package com.accenture.assessment.franchise.usecase;

import com.accenture.assessment.franchise.model.FranchiseTopStock;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class GetTopStockProductsUseCaseImpl implements GetTopStockProductsUseCase {
    private final FranchiseServiceDomain franchiseServiceDomain;

    @Override
    public Mono<FranchiseTopStock> getTopStockProducts(UUID id) {
        return franchiseServiceDomain.assertIdExists(id)
                .then(Mono.defer(() -> franchiseServiceDomain.getTopStockProducts(id)));
    }
}
