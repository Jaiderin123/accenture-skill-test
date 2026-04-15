package com.accenture.assessment.franchise.model;

import java.util.Map;

public record FranchiseTopStock(
        String franchiseName,
        Map<String, ProductWithStock> topProductByBranch
) {}

