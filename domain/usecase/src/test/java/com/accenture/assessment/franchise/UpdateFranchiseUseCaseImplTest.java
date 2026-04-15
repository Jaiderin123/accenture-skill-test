package com.accenture.assessment.franchise;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.franchise.model.Franchise;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import com.accenture.assessment.franchise.usecase.UpdateFranchiseUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateFranchiseUseCaseImplTest {

    @Mock
    private FranchiseServiceDomain franchiseServiceDomain;

    @InjectMocks
    private UpdateFranchiseUseCaseImpl updateFranchiseUseCase;

    @Test
    void shouldUpdateFranchiseSuccessfully() {
        UUID franchiseId = UUID.randomUUID();
        String newName = "Accenture Burger V2";
        Franchise expectedFranchise = new Franchise(franchiseId, newName, List.of());

        when(franchiseServiceDomain.assertNameNotExists(newName))
                .thenReturn(Mono.empty());


        when(franchiseServiceDomain.update(franchiseId, newName))
                .thenReturn(Mono.just(expectedFranchise));


        StepVerifier.create(updateFranchiseUseCase.update(franchiseId, newName))
                .expectNext(expectedFranchise)
                .verifyComplete();

        verify(franchiseServiceDomain)
                .assertNameNotExists(newName);
        verify(franchiseServiceDomain)
                .update(franchiseId, newName);
    }

    @Test
    void shouldFailWhenNewNameAlreadyExists() {
        UUID franchiseId = UUID.randomUUID();
        String newName = "Accenture Burger V2";
        BusinessException expectedException = new BusinessException("Franchise name already exists, please try another");

        when(franchiseServiceDomain.assertNameNotExists(newName))
                .thenReturn(Mono.error(expectedException));

        StepVerifier.create(updateFranchiseUseCase.update(franchiseId, newName))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                        throwable.getMessage().equals("Franchise name already exists, please try another"))
                .verify();

        verify(franchiseServiceDomain)
                .assertNameNotExists(newName);

        verify(franchiseServiceDomain, never()).update(any(), any());
    }
}