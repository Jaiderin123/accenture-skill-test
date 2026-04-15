package com.accenture.assessment.branch;

import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.branch.service.BranchServiceDomain;
import com.accenture.assessment.branch.usecase.UpdateBranchUseCaseImpl;
import com.accenture.assessment.exceptions.BusinessException;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateBranchUseCaseImplTest {

    @Mock
    private BranchServiceDomain branchServiceDomain;

    @InjectMocks
    private UpdateBranchUseCaseImpl updateBranchUseCase;

    private UUID branchId;
    private Branch branch;

    @BeforeEach
    void setUp() {
        branchId = UUID.randomUUID();
        branch = new Branch(
                branchId,
                UUID.randomUUID(),
                "Accenture Branch Update",
                List.of()
        );
    }

    @Test
    void update_whenBranchExists_shouldReturnUpdatedBranch() {
        when(branchServiceDomain.update(branchId, "Accenture Branch Update"))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(updateBranchUseCase.update(branchId, "Accenture Branch Update"))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void update_whenBranchNotFound_shouldPropagateBusinessException() {
        when(branchServiceDomain.update(branchId, "Accenture Branch Update"))
                .thenReturn(Mono.error(new BusinessException("Franchise not found")));

        StepVerifier.create(updateBranchUseCase.update(branchId, "Accenture Branch Update"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Franchise not found"))
                .verify();
    }
}
