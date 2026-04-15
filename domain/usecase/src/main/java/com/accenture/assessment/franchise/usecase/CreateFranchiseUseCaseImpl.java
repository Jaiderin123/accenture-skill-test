package com.accenture.assessment.franchise.usecase;

import com.accenture.assessment.franchise.gateway.FranchiseRepository;
import com.accenture.assessment.franchise.model.Franchise;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateFranchiseUseCaseImpl implements CreateFranchiseUseCase {
    private final FranchiseServiceDomain franchiseServiceDomain;
    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Franchise> createFranchise(String name) {
        return franchiseServiceDomain.assertNameNotExists(name)
                .then(Mono.defer(() -> franchiseRepository.create(name)));
    }
}
