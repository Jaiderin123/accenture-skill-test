package com.accenture.assessment.config.usecase;

import com.accenture.assessment.franchise.gateway.FranchiseRepository;
import com.accenture.assessment.franchise.service.FranchiseServiceDomain;
import com.accenture.assessment.franchise.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FranchiseUseCaseConfig {

    @Bean
    public FranchiseServiceDomain franchiseServiceDomain(FranchiseRepository franchiseRepository){
        return new FranchiseServiceDomain(franchiseRepository);
    }

    @Bean
    public CreateFranchiseUseCase createFranchiseUseCase(FranchiseServiceDomain franchiseServiceDomain, FranchiseRepository repository) {
        return new CreateFranchiseUseCaseImpl(franchiseServiceDomain, repository);
    }

    @Bean
    public UpdateFranchiseUseCase updateFranchiseUseCase(FranchiseServiceDomain franchiseServiceDomain) {
        return new UpdateFranchiseUseCaseImpl(franchiseServiceDomain);
    }
    @Bean
    public GetTopStockProductsUseCase getTopStockProductsUseCase(FranchiseServiceDomain franchiseServiceDomain) {
        return new GetTopStockProductsUseCaseImpl(franchiseServiceDomain);
    }
}
