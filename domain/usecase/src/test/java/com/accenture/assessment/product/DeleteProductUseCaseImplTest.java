package com.accenture.assessment.product;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.service.ProductServiceDomain;
import com.accenture.assessment.product.usecase.DeleteProductUseCaseImpl;
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
class DeleteProductUseCaseImplTest {

    @Mock
    private ProductServiceDomain productServiceDomain;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductUseCaseImpl deleteProductUseCase;

    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
    }

    @Test
    void delete_whenProductExists_shouldCompleteEmpty() {
        when(productServiceDomain.assertIdExists(productId))
                .thenReturn(Mono.empty());

        when(productRepository.delete(productId))
                .thenReturn(Mono.empty());

        StepVerifier.create(deleteProductUseCase.delete(productId))
                .verifyComplete();
    }

    @Test
    void delete_whenProductDoesNotExist_shouldThrowBusinessException() {
        when(productServiceDomain.assertIdExists(productId))
                .thenReturn(Mono.error(new BusinessException("Product don't exists")));

        StepVerifier.create(deleteProductUseCase.delete(productId))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Product don't exists"))
                .verify();

        verify(productRepository, never())
                .delete(productId);
    }
}
