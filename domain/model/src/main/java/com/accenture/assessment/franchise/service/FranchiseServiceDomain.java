package com.accenture.assessment.franchise.service;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.franchise.gateway.FranchiseRepository;
import com.accenture.assessment.franchise.model.Franchise;
import com.accenture.assessment.franchise.model.FranchiseTopStock;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class FranchiseServiceDomain {
    private final FranchiseRepository franchiseRepository;

    public Mono<Void> assertNameNotExists(String name) {
        return franchiseRepository.existsByName(name)
                .flatMap(valExist -> {
                    if (valExist)
                        return Mono.error(new BusinessException("Franchise name already exists, please try another"));
                    return Mono.empty();
                });
    }

    public Mono<Void> assertIdExists(UUID id) {
        return franchiseRepository.existsById(id)
                .flatMap(valExist -> {
                    if (!valExist)
                        return Mono.error(new BusinessException("Franchise doesn't exist"));

                    return Mono.empty();
                });
    }

    public Mono<Franchise> update(UUID id, String name){
        return franchiseRepository.update(id, name)
                .switchIfEmpty(Mono.error(new BusinessException("Franchise not found")));
    }


    public Mono<FranchiseTopStock> getTopStockProducts(UUID id){
        return franchiseRepository.getTopStockProducts(id)
                .switchIfEmpty(Mono.error(new BusinessException("Franchise report empty, please validate the franchise has an relation branches and products")));
    }
}
