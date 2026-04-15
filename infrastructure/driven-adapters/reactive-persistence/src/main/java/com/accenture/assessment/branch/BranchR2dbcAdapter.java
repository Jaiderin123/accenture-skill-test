package com.accenture.assessment.branch;

import com.accenture.assessment.branch.entity.BranchEntity;
import com.accenture.assessment.branch.gateway.BranchRepository;
import com.accenture.assessment.branch.mapper.BranchMapper;
import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.branch.r2dbc.BranchR2dbcRepository;
import com.accenture.assessment.config.R2dbcErrorMapper;
import com.accenture.assessment.product.entity.ProductEntity;
import com.accenture.assessment.product.r2dbc.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BranchR2dbcAdapter implements BranchRepository{
    private final BranchR2dbcRepository branchR2dbcRepository;
    private final ProductR2dbcRepository productR2dbcRepository;
    private final BranchMapper branchMapper;
    private final R2dbcErrorMapper r2dbcErrorMapper;


    @Override
    public Mono<Boolean> existsByName(String name) {
        return branchR2dbcRepository.existsByName(name)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Branch> create(UUID franchiseId, String name) {
        BranchEntity branchEntity = BranchEntity.builder()
                .franchiseId(franchiseId)
                .name(name)
                .build();

        return branchR2dbcRepository.save(branchEntity)
                .map(branchMapper::toModel)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Boolean> existsById(UUID branchId) {
        return branchR2dbcRepository.existsById(branchId)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Branch> update(UUID id, String name) {
        return branchR2dbcRepository.update(id, name)
                .flatMap(rowsAffected -> {
                    if (rowsAffected == 0)
                        return Mono.empty();

                    return Mono.zip(
                            branchR2dbcRepository.findById(id),
                            productR2dbcRepository.findAllByBranchId(id).collectList()
                    ).map(tuple -> {
                        BranchEntity branchEntity = tuple.getT1();
                        List<ProductEntity> productEntities = tuple.getT2();

                        return branchMapper.toModel(branchEntity, productEntities);
                    });
                })
                .transform(r2dbcErrorMapper.onErrorMap());
    }
}
