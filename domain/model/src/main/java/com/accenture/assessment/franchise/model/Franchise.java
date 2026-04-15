package com.accenture.assessment.franchise.model;

import com.accenture.assessment.branch.model.Branch;

import java.util.List;
import java.util.UUID;

public record Franchise(
        UUID id,
        String name,
        List<Branch> branches
) {}
