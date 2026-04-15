package com.accenture.assessment.franchise.usecase;

import com.accenture.assessment.franchise.model.Franchise;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateFranchiseUseCaseImpl implements UpdateFranchiseUseCase {
    private final FranchiseServiceDomain franchiseServiceDomain;

    @Override
    public Mono<Franchise> update(UUID id, String newName) {
        return franchiseServiceDomain.assertNameNotExists(newName)
                .then(Mono.defer(() -> franchiseServiceDomain.update(id, newName)));
    }
}
