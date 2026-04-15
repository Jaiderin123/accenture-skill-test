package com.accenture.assessment.product.gateway;

import com.accenture.assessment.product.model.Product;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepository {
    Mono<Boolean> existsByName(String name);

    Mono<String> findNameById(UUID id);

    Mono<Product> create(UUID branchId, String name, Integer stock);

    Mono<Product> updateName(UUID id, String name);

    Mono<Boolean> existsById(UUID id);

    Mono<Product> updateStock(UUID id, Integer stock);

    Mono<Void> delete(UUID id);

    //Validations


}
