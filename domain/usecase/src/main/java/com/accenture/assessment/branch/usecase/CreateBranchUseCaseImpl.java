package com.accenture.assessment.branch.usecase;

import com.accenture.assessment.branch.gateway.BranchRepository;
import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.branch.service.BranchServiceDomain;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateBranchUseCaseImpl implements CreateBranchUseCase {
    private final BranchServiceDomain branchServiceDomain;
    private final BranchRepository branchRepository;
    private final FranchiseServiceDomain franchiseServiceDomain;

    @Override
    public Mono<Branch> create(UUID franchiseId, String name) {
        return franchiseServiceDomain.assertIdExists(franchiseId)
                .then(Mono.defer(() ->
                        branchServiceDomain.assertNameNotExists(name)
                                .then(Mono.defer(() -> branchRepository.create(franchiseId, name)))
                        )
                );
    }
}
