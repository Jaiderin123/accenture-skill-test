package com.accenture.assessment.franchise.usecase;

import com.accenture.assessment.franchise.model.FranchiseTopStock;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetTopStockProductsUseCase {
    Mono<FranchiseTopStock> getTopStockProducts(UUID id);
}
