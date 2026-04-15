package com.accenture.assessment.product;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.model.Product;
import com.accenture.assessment.product.service.ProductServiceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceDomainTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceDomain productServiceDomain;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        product = new Product(
                productId,
                UUID.randomUUID(),
                "Test Product",
                10
        );
    }

    @Test
    void assertNameNotExists_whenNameIsFree_shouldCompleteEmpty() {
        when(productRepository.existsByName("New Product"))
                .thenReturn(Mono.just(false));

        StepVerifier.create(productServiceDomain.assertNameNotExists("New Product"))
                .verifyComplete();
    }

    @Test
    void assertNameNotExists_whenNameAlreadyExists_shouldThrowBusinessException() {
        when(productRepository.existsByName("Existing Product"))
                .thenReturn(Mono.just(true));

        StepVerifier.create(productServiceDomain.assertNameNotExists("Existing Product"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product already exists, please change the product name"))
                .verify();
    }

    @Test
    void assertIdExists_whenProductExists_shouldCompleteEmpty() {
        when(productRepository.existsById(productId))
                .thenReturn(Mono.just(true));

        StepVerifier.create(productServiceDomain.assertIdExists(productId))
                .verifyComplete();
    }

    @Test
    void assertIdExists_whenProductDoesNotExist_shouldThrowBusinessException() {
        when(productRepository.existsById(productId))
                .thenReturn(Mono.just(false));

        StepVerifier.create(productServiceDomain.assertIdExists(productId))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product don't exists"))
                .verify();
    }

    @Test
    void validateNewNameToChange_whenNewNameIsTheSameAsCurrent_shouldCompleteEmpty() {
        when(productRepository.findNameById(productId)).thenReturn(Mono.just("Same Name"));

        StepVerifier.create(productServiceDomain.validateNewNameToChange(productId, "Same Name"))
                .verifyComplete();
    }

    @Test
    void validateNewNameToChange_whenNewNameIsDifferentAndFree_shouldCompleteEmpty() {
        when(productRepository.findNameById(productId))
                .thenReturn(Mono.just("Old Name"));
        when(productRepository.existsByName("New Name"))
                .thenReturn(Mono.just(false));

        StepVerifier.create(productServiceDomain.validateNewNameToChange(productId, "New Name"))
                .verifyComplete();
    }

    @Test
    void validateNewNameToChange_whenNewNameIsDifferentButTaken_shouldThrowBusinessException() {
        when(productRepository.findNameById(productId))
                .thenReturn(Mono.just("Old Name"));
        when(productRepository.existsByName("Taken Name"))
                .thenReturn(Mono.just(true));

        StepVerifier.create(productServiceDomain.validateNewNameToChange(productId, "Taken Name"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product already exists, please change the product name"))
                .verify();
    }

    @Test
    void updateName_whenProductExists_shouldReturnUpdatedProduct() {
        when(productRepository.updateName(productId, "Accenture Product"))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productServiceDomain.updateName(productId, "Accenture Product"))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void updateName_whenProductNotFound_shouldThrowBusinessException() {
        when(productRepository.updateName(productId, "Accenture Product"))
                .thenReturn(Mono.empty());

        StepVerifier.create(productServiceDomain.updateName(productId, "Accenture Product"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product not found"))
                .verify();
    }

    @Test
    void updateStock_whenProductExists_shouldReturnUpdatedProduct() {
        when(productRepository.updateStock(productId, 50)).thenReturn(Mono.just(product));

        StepVerifier.create(productServiceDomain.updateStock(productId, 50))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void updateStock_whenProductNotFound_shouldThrowBusinessException() {
        when(productRepository.updateStock(productId, 50))
                .thenReturn(Mono.empty());

        StepVerifier.create(productServiceDomain.updateStock(productId, 50))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product not found"))
                .verify();
    }
}
