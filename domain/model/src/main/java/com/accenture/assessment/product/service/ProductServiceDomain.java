package com.accenture.assessment.product.service;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.model.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class ProductServiceDomain {
    private final ProductRepository productRepository;

    public Mono<Void> assertNameNotExists(String name){
        return productRepository.existsByName(name)
                .flatMap(exists ->
                        exists
                        ?
                        Mono.error(new BusinessException("Product already exists, please change the product name"))
                        :
                        Mono.empty()
                );
    }

    public Mono<Void> assertIdExists(UUID id){
        return productRepository.existsById(id)
                .flatMap(exists ->
                        !exists
                        ?
                        Mono.error(new BusinessException("Product don't exists"))
                        : Mono.empty()
                );
    }

    public Mono<Void> validateNewNameToChange(UUID id, String newName) {
        return productRepository.findNameById(id)
                .flatMap(actualName -> {
                    if (actualName.equals(newName))
                        return Mono.empty();
                    else
                        return assertNameNotExists(newName);
                });
    }

    public Mono<Product> updateName(UUID id, String name){
        return productRepository.updateName(id, name)
                .switchIfEmpty(Mono.error(new BusinessException("Product not found")));
    }

    public Mono<Product> updateStock(UUID id, Integer stock){
        return productRepository.updateStock(id, stock)
                .switchIfEmpty(Mono.error(new BusinessException("Product not found")));
    }
}
