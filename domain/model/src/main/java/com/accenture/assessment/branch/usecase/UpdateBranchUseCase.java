package com.accenture.assessment.branch.usecase;

import com.accenture.assessment.branch.model.Branch;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UpdateBranchUseCase {
    Mono<Branch> update(UUID id, String name);
}
