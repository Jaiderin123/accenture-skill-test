package com.accenture.assessment.franchise.usecase;

import com.accenture.assessment.franchise.model.Franchise;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UpdateFranchiseUseCase {
    Mono<Franchise> update(UUID id, String newName);
}
