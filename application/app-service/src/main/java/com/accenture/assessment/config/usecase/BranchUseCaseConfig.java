package com.accenture.assessment.config.usecase;

import com.accenture.assessment.branch.gateway.BranchRepository;
import com.accenture.assessment.branch.service.BranchServiceDomain;
import com.accenture.assessment.branch.usecase.CreateBranchUseCase;
import com.accenture.assessment.branch.usecase.CreateBranchUseCaseImpl;
import com.accenture.assessment.branch.usecase.UpdateBranchUseCase;
import com.accenture.assessment.branch.usecase.UpdateBranchUseCaseImpl;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BranchUseCaseConfig {

    @Bean
    public BranchServiceDomain branchServiceDomain(BranchRepository branchRepository) {
        return new BranchServiceDomain(branchRepository);
    }

    @Bean
    public CreateBranchUseCase createBranchUseCase(BranchServiceDomain branchServiceDomain, BranchRepository branchRepository, FranchiseServiceDomain franchiseServiceDomain) {
        return new CreateBranchUseCaseImpl(branchServiceDomain, branchRepository, franchiseServiceDomain);
    }

    @Bean
    public UpdateBranchUseCase updateBranchUseCase(BranchServiceDomain branchServiceDomain) {
        return new UpdateBranchUseCaseImpl(branchServiceDomain);
    }

}
