package com.accenture.assessment.franchise;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.franchise.gateway.FranchiseRepository;
import com.accenture.assessment.franchise.model.Franchise;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import com.accenture.assessment.franchise.usecase.CreateFranchiseUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateFranchiseUseCaseImplTest {

    @Mock
    private FranchiseServiceDomain franchiseServiceDomain;

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private CreateFranchiseUseCaseImpl createFranchiseUseCase;

    @Test
    void shouldCreateFranchiseSuccessfully() {
        String franchiseName = "Accenture Burger";

        Franchise expectedFranchise = new Franchise(
                UUID.randomUUID(),
                franchiseName,
                List.of()
        );

        when(franchiseServiceDomain.assertNameNotExists(franchiseName))
                .thenReturn(Mono.empty());

        when(franchiseRepository.create(franchiseName))
                .thenReturn(Mono.just(expectedFranchise));

        StepVerifier.create(createFranchiseUseCase.createFranchise(franchiseName))
                .expectNext(expectedFranchise)
                .verifyComplete();

        verify(franchiseServiceDomain)
                .assertNameNotExists(franchiseName);
        verify(franchiseRepository)
                .create(franchiseName);
    }

    @Test
    void shouldFailWhenFranchiseNameAlreadyExists() {
        String franchiseName = "Accenture Burger";
        BusinessException expectedException = new BusinessException("Franchise name already exists, please try another");

        when(franchiseServiceDomain.assertNameNotExists(franchiseName))
                .thenReturn(Mono.error(expectedException));

        StepVerifier.create(createFranchiseUseCase.createFranchise(franchiseName))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                        throwable.getMessage().equals("Franchise name already exists, please try another"))
                .verify();

        verify(franchiseServiceDomain)
                .assertNameNotExists(franchiseName);
        // block db save if validation fails
        verifyNoInteractions(franchiseRepository);
    }
}