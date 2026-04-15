package com.accenture.assessment.product.r2dbc;

import com.accenture.assessment.product.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductR2dbcRepository extends R2dbcRepository<ProductEntity, UUID> {
    Mono<Boolean> existsByName(String name);

    @Modifying
    @Query("UPDATE products SET name = :name WHERE id = :id")
    Mono<Integer> updateName(UUID id, String name);

    @Modifying
    @Query("UPDATE products SET stock = :stock WHERE id = :id")
    Mono<Integer> updateStock(UUID id, Integer stock);

    Flux<ProductEntity> findAllByBranchId(UUID id);

    Mono<String> findNameById(UUID id);
}
