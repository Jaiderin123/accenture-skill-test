package com.accenture.assessment.branch;

import com.accenture.assessment.branch.gateway.BranchRepository;
import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.branch.service.BranchServiceDomain;
import com.accenture.assessment.branch.usecase.CreateBranchUseCaseImpl;
import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBranchUseCaseImplTest {

    @Mock
    private BranchServiceDomain branchServiceDomain;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private FranchiseServiceDomain franchiseServiceDomain;

    @InjectMocks
    private CreateBranchUseCaseImpl createBranchUseCase;

    private UUID franchiseId;
    private Branch branch;

    @BeforeEach
    void setUp() {
        franchiseId = UUID.randomUUID();
        branch = new Branch(
                UUID.randomUUID(),
                franchiseId,
                "Accenture Branch",
                List.of()
        );
    }

    @Test
    void create_whenFranchiseExistsAndNameIsFree_shouldReturnCreatedBranch() {
        when(franchiseServiceDomain.assertIdExists(franchiseId))
                .thenReturn(Mono.empty());
        when(branchServiceDomain.assertNameNotExists("Accenture Branch"))
                .thenReturn(Mono.empty());
        when(branchRepository.create(franchiseId, "Accenture Branch"))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(createBranchUseCase.create(franchiseId, "Accenture Branch"))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void create_whenFranchiseDoesNotExist_shouldThrowBusinessException() {
        when(franchiseServiceDomain.assertIdExists(franchiseId))
                .thenReturn(Mono.error(new BusinessException("Franchise doesn't exist")));

        StepVerifier.create(createBranchUseCase.create(franchiseId, "Accenture Branch"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Franchise doesn't exist"))
                .verify();

        verify(branchServiceDomain, never()).
                assertNameNotExists("Accenture Branch");

        verify(branchRepository, never())
                .create(franchiseId, "Accenture Branch");
    }

    @Test
    void create_whenFranchiseExistsButNameIsTaken_shouldThrowBusinessException() {
        when(franchiseServiceDomain.assertIdExists(franchiseId))
                .thenReturn(Mono.empty());

        when(branchServiceDomain.assertNameNotExists("Existing Branch"))
                .thenReturn(Mono.error(new BusinessException("Branch already exists")));

        StepVerifier.create(createBranchUseCase.create(franchiseId, "Existing Branch"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Branch already exists"))
                .verify();

        verify(branchRepository, never())
                .create(franchiseId, "Existing Branch");
    }
}
