package com.accenture.assessment.product;

import com.accenture.assessment.branch.service.BranchServiceDomain;
import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.model.Product;
import com.accenture.assessment.product.service.ProductServiceDomain;
import com.accenture.assessment.product.usecase.CreateProductUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseImplTest {

    @Mock
    private BranchServiceDomain branchServiceDomain;

    @Mock
    private ProductServiceDomain productServiceDomain;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;

    private UUID branchId;
    private Product product;

    @BeforeEach
    void setUp() {
        branchId = UUID.randomUUID();
        product = new Product(
                UUID.randomUUID(),
                branchId,
                "New Product",
                10
        );
    }

    @Test
    void create_whenBranchExistsAndNameIsFree_shouldReturnCreatedProduct() {
        when(branchServiceDomain.assertIdExists(branchId))
                .thenReturn(Mono.empty());
        when(productServiceDomain.assertNameNotExists("New Product"))
                .thenReturn(Mono.empty());
        when(productRepository.create(branchId, "New Product", 10))
                .thenReturn(Mono.just(product));

        StepVerifier.create(createProductUseCase.create(branchId, "New Product", 10))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void create_whenBranchDoesNotExist_shouldThrowBusinessException() {
        when(branchServiceDomain.assertIdExists(branchId))
                .thenReturn(Mono.error(new BusinessException("Branch already exists")));

        StepVerifier.create(createProductUseCase.create(branchId, "New Product", 10))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Branch already exists"))
                .verify();

        verify(productServiceDomain, never())
                .assertNameNotExists("New Product");

        verify(productRepository, never())
                .create(branchId, "New Product", 10);
    }

    @Test
    void create_whenBranchExistsButProductNameIsTaken_shouldThrowBusinessException() {
        when(branchServiceDomain.assertIdExists(branchId))
                .thenReturn(Mono.empty());
        when(productServiceDomain.assertNameNotExists("Existing Product"))
                .thenReturn(Mono.error(new BusinessException("Product already exists, please change the product name")));

        StepVerifier.create(createProductUseCase.create(branchId, "Existing Product", 10))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product already exists, please change the product name"))
                .verify();

        verify(productRepository, never())
                .create(branchId, "Existing Product", 10);
    }
}
