package com.accenture.assessment.product;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.product.model.Product;
import com.accenture.assessment.product.service.ProductServiceDomain;
import com.accenture.assessment.product.usecase.UpdateProductNameUseCaseImpl;
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
class UpdateProductNameUseCaseImplTest {

    @Mock
    private ProductServiceDomain productServiceDomain;

    @InjectMocks
    private UpdateProductNameUseCaseImpl updateProductNameUseCase;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        product = new Product(
                productId,
                UUID.randomUUID(),
                "Accenture Product",
                10);
    }

    @Test
    void updateName_whenValidationPassesAndProductExists_shouldReturnUpdatedProduct() {
        when(productServiceDomain.validateNewNameToChange(productId, "Accenture Product"))
                .thenReturn(Mono.empty());

        when(productServiceDomain.updateName(productId, "Accenture Product"))
                .thenReturn(Mono.just(product));

        StepVerifier.create(updateProductNameUseCase.updateName(productId, "Accenture Product"))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void updateName_whenNameIsTaken_shouldThrowBusinessException() {
        when(productServiceDomain.validateNewNameToChange(productId, "Taken Name"))
                .thenReturn(Mono.error(new BusinessException("Product already exists, please change the product name")));

        StepVerifier.create(updateProductNameUseCase.updateName(productId, "Taken Name"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product already exists, please change the product name"))
                .verify();
    }

    @Test
    void updateName_whenProductNotFound_shouldThrowBusinessException() {
        when(productServiceDomain.validateNewNameToChange(productId, "Accenture Product"))
                .thenReturn(Mono.empty());

        when(productServiceDomain.updateName(productId, "Accenture Product"))
                .thenReturn(Mono.error(new BusinessException("Product not found")));

        StepVerifier.create(updateProductNameUseCase.updateName(productId, "Accenture Product"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product not found"))
                .verify();
    }
}
