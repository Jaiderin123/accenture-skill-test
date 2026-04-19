package com.accenture.assessment.branch.service;

import com.accenture.assessment.branch.gateway.BranchRepository;
import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class BranchServiceDomain {
    private final BranchRepository branchRepository;

    public Mono<Void> assertNameNotExists(String name){
        return branchRepository.existsByName(name)
                .flatMap(exists ->
                        exists
                        ?
                        Mono.error(new BusinessException("Branch already exists"))
                        :
                        Mono.empty()
                );
    }

    public Mono<Branch> update(UUID id, String name){
        return branchRepository.update(id, name)
                .switchIfEmpty(Mono.error(new BusinessException("Branch not found")));
    }

    public Mono<Void> assertIdExists(UUID id){
        return branchRepository.existsById(id)
                .flatMap(exists ->
                        !exists
                        ?
                        Mono.error(new BusinessException("Branch doesn't exist"))
                        :
                        Mono.empty());
    }
}
