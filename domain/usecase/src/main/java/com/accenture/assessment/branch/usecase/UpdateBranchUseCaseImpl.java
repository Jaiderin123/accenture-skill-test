package com.accenture.assessment.branch.usecase;

import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.branch.service.BranchServiceDomain;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateBranchUseCaseImpl implements UpdateBranchUseCase {
    private final BranchServiceDomain branchServiceDomain;

    @Override
    public Mono<Branch> update(UUID id, String name) {
        return branchServiceDomain.update(id, name);
    }
}
