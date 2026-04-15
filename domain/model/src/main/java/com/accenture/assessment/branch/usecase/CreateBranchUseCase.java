package com.accenture.assessment.branch.usecase;

import com.accenture.assessment.branch.model.Branch;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CreateBranchUseCase {
    Mono<Branch> create(UUID franchiseId, String name);
}
