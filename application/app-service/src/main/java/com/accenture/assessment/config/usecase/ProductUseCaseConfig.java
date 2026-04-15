package com.accenture.assessment.config.usecase;

import com.accenture.assessment.branch.service.BranchServiceDomain;
import com.accenture.assessment.product.gateway.ProductRepository;
import com.accenture.assessment.product.service.ProductServiceDomain;
import com.accenture.assessment.product.usecase.CreateProductUseCase;
import com.accenture.assessment.product.usecase.CreateProductUseCaseImpl;
import com.accenture.assessment.product.usecase.DeleteProductUseCase;
import com.accenture.assessment.product.usecase.DeleteProductUseCaseImpl;
import com.accenture.assessment.product.usecase.UpdateProductNameUseCase;
import com.accenture.assessment.product.usecase.UpdateProductNameUseCaseImpl;
import com.accenture.assessment.product.usecase.UpdateProductStockUseCase;
import com.accenture.assessment.product.usecase.UpdateProductStockUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfig {
    @Bean
    public ProductServiceDomain productServiceDomain(ProductRepository productRepository) {
        return new ProductServiceDomain(productRepository);
    }

    @Bean
    public CreateProductUseCase createProductUseCase(BranchServiceDomain branchServiceDomain, ProductServiceDomain productServiceDomain, ProductRepository productRepository) {
        return new CreateProductUseCaseImpl(branchServiceDomain, productServiceDomain, productRepository);
    }

    @Bean
    public UpdateProductNameUseCase updateProductUseCase(ProductServiceDomain productServiceDomain) {
        return new UpdateProductNameUseCaseImpl(productServiceDomain);
    }

    @Bean
    public UpdateProductStockUseCase updateProductStockUseCase(ProductServiceDomain productServiceDomain) {
        return new UpdateProductStockUseCaseImpl(productServiceDomain);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductServiceDomain productServiceDomain, ProductRepository productRepository) {
        return new DeleteProductUseCaseImpl(productServiceDomain, productRepository);
    }
}
