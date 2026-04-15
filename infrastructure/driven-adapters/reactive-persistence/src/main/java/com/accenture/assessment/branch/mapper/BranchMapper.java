package com.accenture.assessment.branch.mapper;

import com.accenture.assessment.branch.entity.BranchEntity;
import com.accenture.assessment.branch.model.Branch;
import com.accenture.assessment.product.mapper.ProductMapper;
import com.accenture.assessment.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BranchMapper {
    private final ProductMapper productMapper;

    public Branch toModel(BranchEntity branchEntity) {
        return new Branch(
                branchEntity.getId(),
                branchEntity.getFranchiseId(),
                branchEntity.getName(),
                List.of()
        );
    }

    public Branch toModel(BranchEntity branchEntity, List<ProductEntity> productEntities) {
        return new Branch(
                branchEntity.getId(),
                branchEntity.getFranchiseId(),
                branchEntity.getName(),
                productEntities.stream()
                        .map(productMapper::toModel)
                        .toList());
    }

}
