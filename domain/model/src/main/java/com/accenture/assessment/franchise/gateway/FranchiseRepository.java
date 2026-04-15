package com.accenture.assessment.franchise.gateway;

import com.accenture.assessment.franchise.model.Franchise;
import com.accenture.assessment.franchise.model.FranchiseTopStock;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FranchiseRepository {
    Mono<Boolean> existsByName(String name);

    Mono<Franchise> create(String name);

    Mono<Franchise> update(UUID id, String newName);

    Mono<Boolean> existsById(UUID id);

    Mono<FranchiseTopStock> getTopStockProducts(UUID id);
}
