package com.accenture.assessment.franchise.usecase;

import com.accenture.assessment.franchise.model.Franchise;
import reactor.core.publisher.Mono;

public interface CreateFranchiseUseCase {
    Mono<Franchise> createFranchise(String name);
}
