package com.accenture.assessment.franchise;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.franchise.gateway.FranchiseRepository;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceDomainTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseServiceDomain franchiseServiceDomain;

    @Test
    void assertNameNotExists_ShouldReturnEmptyWhenNameIsAvailable() {
        String name = "New Tech Franchise";
        when(franchiseRepository.existsByName(name))
                .thenReturn(Mono.just(false));

        StepVerifier.create(franchiseServiceDomain.assertNameNotExists(name))
                .verifyComplete();

        verify(franchiseRepository)
                .existsByName(name);
    }

    @Test
    void assertNameNotExists_ShouldThrowErrorWhenNameExists() {
        String name = "Existing Franchise";
        when(franchiseRepository.existsByName(name))
                .thenReturn(Mono.just(true));

        StepVerifier.create(franchiseServiceDomain.assertNameNotExists(name))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                        throwable.getMessage().equals("Franchise name already exists, please try another"))
                .verify();

        verify(franchiseRepository)
                .existsByName(name);
    }
}
