package com.accenture.assessment.branch;

import com.accenture.assessment.branch.gateway.BranchRepository;
import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.branch.service.BranchServiceDomain;
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
class BranchServiceDomainTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceDomain branchServiceDomain;

    private UUID branchId;
    private Branch branch;

    @BeforeEach
    void setUp() {
        branchId = UUID.randomUUID();
        branch = new Branch(
                branchId,
                UUID.randomUUID(),
                "Accenture Branch",
                List.of()
        );
    }

    @Test
    void assertNameNotExists_whenNameIsFree_shouldCompleteEmpty() {
        when(branchRepository.existsByName("Accenture Branch"))
                .thenReturn(Mono.just(false));

        StepVerifier.create(branchServiceDomain.assertNameNotExists("Accenture Branch"))
                .verifyComplete();
    }

    @Test
    void assertNameNotExists_whenNameAlreadyExists_shouldThrowBusinessException() {
        String name = "Accenture Branch";
        when(branchRepository.existsByName(name))
                .thenReturn(Mono.just(true));

        StepVerifier.create(branchServiceDomain.assertNameNotExists(name))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Branch already exists"))
                .verify();
    }

    @Test
    void update_whenBranchExists_shouldReturnUpdatedBranch() {
        when(branchRepository.update(branchId, "Updated Name"))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchServiceDomain.update(branchId, "Updated Name"))
                .expectNext(branch)
                .verifyComplete();
    }

    @Test
    void update_whenBranchNotFound_shouldThrowBusinessException() {
        when(branchRepository.update(branchId, "Updated Name"))
                .thenReturn(Mono.empty());

        StepVerifier.create(branchServiceDomain.update(branchId, "Updated Name"))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Franchise not found"))
                .verify();
    }

    @Test
    void assertIdExists_whenBranchExists_shouldCompleteEmpty() {
        when(branchRepository.existsById(branchId))
                .thenReturn(Mono.just(true));

        StepVerifier.create(branchServiceDomain.assertIdExists(branchId))
                .verifyComplete();
    }

    @Test
    void assertIdExists_whenBranchDoesNotExist_shouldThrowBusinessException() {
        when(branchRepository.existsById(branchId))
                .thenReturn(Mono.just(false));

        StepVerifier.create(branchServiceDomain.assertIdExists(branchId))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ex.getMessage().equals("Branch already exists"))
                .verify();
    }
}