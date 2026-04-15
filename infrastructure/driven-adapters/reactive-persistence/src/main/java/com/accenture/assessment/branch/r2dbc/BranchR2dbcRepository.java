package com.accenture.assessment.branch.r2dbc;

import com.accenture.assessment.branch.entity.BranchEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BranchR2dbcRepository extends R2dbcRepository<BranchEntity, UUID> {
    Mono<Boolean> existsByName(String name);

    Flux<BranchEntity> findAllByFranchiseId(UUID franchiseId);
    @Modifying
    @Query("UPDATE branches SET name = :name WHERE id = :id")
    Mono<Integer> update(UUID id, String name);
}
