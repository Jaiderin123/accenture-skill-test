package com.accenture.assessment.franchise;

import com.accenture.assessment.branch.entity.BranchEntity;
import com.accenture.assessment.branch.r2dbc.BranchR2dbcRepository;
import com.accenture.assessment.config.R2dbcErrorMapper;
import com.accenture.assessment.franchise.entity.FranchiseEntity;
import com.accenture.assessment.franchise.entity.FranchiseTopStockProjection;
import com.accenture.assessment.franchise.gateway.FranchiseRepository;
import com.accenture.assessment.franchise.mapper.FranchiseMapper;
import com.accenture.assessment.franchise.model.Franchise;
import com.accenture.assessment.franchise.model.FranchiseTopStock;
import com.accenture.assessment.franchise.model.ProductWithStock;
import com.accenture.assessment.franchise.r2dbc.FranchiseR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
public class FranchiseR2dbcAdapter implements FranchiseRepository {
    private final FranchiseR2dbcRepository franchiseR2dbcRepository;
    private final FranchiseMapper franchiseMapper;
    private final BranchR2dbcRepository branchR2dbcRepository;
    private final R2dbcErrorMapper r2dbcErrorMapper;


    @Override
    public Mono<Boolean> existsByName(String name) {
        return franchiseR2dbcRepository.existsByName(name)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Franchise> create(String name) {
        FranchiseEntity franchiseEntity = FranchiseEntity.builder()
                .name(name)
                .build();

        return franchiseR2dbcRepository.save(franchiseEntity)
                .map(franchiseMapper::toModel)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return franchiseR2dbcRepository.existsById(id)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Franchise> update(UUID id, String newName) {
        return franchiseR2dbcRepository.updateName(id, newName)
                .flatMap(rowsAffected -> {
                    if (rowsAffected == 0)
                        return Mono.empty();

                    return Mono.zip(
                            franchiseR2dbcRepository.findById(id),
                            branchR2dbcRepository.findAllByFranchiseId(id).collectList()
                    ).map(tuple -> {
                        FranchiseEntity franchiseEntity = tuple.getT1();
                        List<BranchEntity> branchEntities = tuple.getT2();

                        return franchiseMapper.toModel(franchiseEntity, branchEntities);
                    });
                })
                .transform(r2dbcErrorMapper.onErrorMap());
    }


    @Override
    public Mono<FranchiseTopStock> getTopStockProducts(UUID id) {
        return franchiseR2dbcRepository.getTopStockProductsByFranchise(id)
                .collectList()
                .flatMap(stockTempList -> {
                    if (stockTempList.isEmpty())
                        return Mono.empty();

                    String franchiseName = stockTempList.getFirst().franchiseName();

                    Map<String, ProductWithStock> productWithStock = stockTempList.stream()
                            .collect(
                                    toMap(
                                            FranchiseTopStockProjection::branchName,
                                            projection -> new ProductWithStock(
                                            projection.productName(),
                                            projection.stock()
                                    )
                            ));

                    return  Mono.just(new FranchiseTopStock(franchiseName, productWithStock));
                })
                .transform(r2dbcErrorMapper.onErrorMap());
    }
}
