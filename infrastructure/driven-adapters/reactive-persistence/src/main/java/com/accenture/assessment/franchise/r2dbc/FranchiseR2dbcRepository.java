package com.accenture.assessment.franchise.r2dbc;

import com.accenture.assessment.franchise.entity.FranchiseEntity;
import com.accenture.assessment.franchise.entity.FranchiseTopStockProjection;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FranchiseR2dbcRepository extends R2dbcRepository<FranchiseEntity, UUID> {
    Mono<Boolean> existsByName(String name);

    @Modifying
    @Query("UPDATE franchises SET name = :name WHERE id = :id")
    Mono<Integer> updateName(UUID id, String name);

    @Query("""
        SELECT
            f.name AS franchise_name,
            b.name AS branch_name,
            p.name AS product_name,
            p.stock AS stock
        FROM (
            SELECT *,
            ROW_NUMBER() OVER (PARTITION BY branch_id ORDER BY stock DESC) as rn
            FROM products
        ) p
        JOIN branches b ON p.branch_id = b.id
        JOIN franchises f ON b.franchise_id = f.id
        WHERE p.rn = 1 AND f.id = :franchiseId
    """)
    Flux<FranchiseTopStockProjection> getTopStockProductsByFranchise(UUID franchiseId);
}
