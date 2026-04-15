package com.accenture.assessment.branch.gateway;

import com.accenture.assessment.branch.model.Branch;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BranchRepository {
    Mono<Boolean> existsByName(String name);

    Mono<Branch> update(UUID id, String name);

    Mono<Boolean> existsById(UUID branchId);

    Mono<Branch> create(UUID franchiseId, String name);
}
