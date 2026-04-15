package com.accenture.assessment.franchise;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.franchise.model.FranchiseTopStock;
import com.accenture.assessment.franchise.model.ProductWithStock;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import com.accenture.assessment.franchise.usecase.GetTopStockProductsUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTopStockProductsUseCaseImplTest {

    @Mock
    private FranchiseServiceDomain franchiseServiceDomain;

    @InjectMocks
    private GetTopStockProductsUseCaseImpl getTopStockProductsUseCase;

    @Test
    void shouldGetTopStockProductsSuccessfully() {
        UUID franchiseId = UUID.randomUUID();
        ProductWithStock topProduct = new ProductWithStock("Doble Carne", 150);

        Map<String, ProductWithStock> topProductsByBranch = Map.of(
                "Sede Poblado", topProduct
        );

        FranchiseTopStock expectedReport = new FranchiseTopStock("Accenture Burger", topProductsByBranch);

        when(franchiseServiceDomain.assertIdExists(franchiseId))
                .thenReturn(Mono.empty());

        when(franchiseServiceDomain.getTopStockProducts(franchiseId))
                .thenReturn(Mono.just(expectedReport));

        StepVerifier.create(getTopStockProductsUseCase.getTopStockProducts(franchiseId))
                .expectNext(expectedReport)
                .verifyComplete();

        verify(franchiseServiceDomain)
                .assertIdExists(franchiseId);
        verify(franchiseServiceDomain)
                .getTopStockProducts(franchiseId);
    }

    @Test
    void shouldFailWhenFranchiseIdDoesNotExist() {
        UUID franchiseId = UUID.randomUUID();
        BusinessException expectedException = new BusinessException("Franchise doesn't exist");

        when(franchiseServiceDomain.assertIdExists(franchiseId))
                .thenReturn(Mono.error(expectedException));

        StepVerifier.create(getTopStockProductsUseCase.getTopStockProducts(franchiseId))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                        throwable.getMessage().equals("Franchise doesn't exist"))
                .verify();

        verify(franchiseServiceDomain)
                .assertIdExists(franchiseId);
        verify(franchiseServiceDomain, never())
                .getTopStockProducts(any());
    }
}