package com.accenture.assessment.franchise.entity;

import org.springframework.beans.factory.annotation.Value;

public record FranchiseTopStockProjection(
        @Value("franchise_name")
        String franchiseName,
        @Value("branch_name")
        String branchName,
        @Value("product_name")
        String productName,
        @Value("stock")
        Integer stock
) {}