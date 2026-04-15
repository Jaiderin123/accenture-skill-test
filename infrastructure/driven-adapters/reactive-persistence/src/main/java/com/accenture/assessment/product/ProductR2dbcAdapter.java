package com.accenture.assessment.product;

import com.accenture.assessment.config.R2dbcErrorMapper;
import com.accenture.assessment.product.entity.ProductEntity;
import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.mapper.ProductMapper;
import com.accenture.assessment.product.model.Product;
import com.accenture.assessment.product.r2dbc.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductR2dbcAdapter implements ProductRepository {
    private final ProductR2dbcRepository productR2dbcRepository;
    private final ProductMapper productMapper;
    private final R2dbcErrorMapper r2dbcErrorMapper;

    @Override
    public Mono<Boolean> existsByName(String name) {
        return productR2dbcRepository.existsByName(name)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Product> create(UUID branchId, String name, Integer stock) {
        ProductEntity productEntity = ProductEntity.builder()
                .branchId(branchId)
                .name(name)
                .stock(stock)
                .build();

        return productR2dbcRepository.save(productEntity)
                .map(productMapper::toModel)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Product> updateName(UUID id, String name) {
        return productR2dbcRepository.updateName(id, name)
                .filter(rowsAffected -> rowsAffected > 0)
                .flatMap(rows -> productR2dbcRepository.findById(id))
                .map(productMapper::toModel)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return productR2dbcRepository.existsById(id)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Product> updateStock(UUID id, Integer stock) {
        return productR2dbcRepository.updateStock(id, stock)
                .filter(rowsAffected -> rowsAffected > 0)
                .flatMap(row -> productR2dbcRepository.findById(id))
                .map(productMapper::toModel)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return productR2dbcRepository.deleteById(id)
                .transform(r2dbcErrorMapper.onErrorMap());
    }

    @Override
    public Mono<String> findNameById(UUID id) {
        return productR2dbcRepository.findNameById(id)
                .transform(r2dbcErrorMapper.onErrorMap());
    }
}
