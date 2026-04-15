package com.accenture.assessment.franchise.mapper;

import com.accenture.assessment.branch.mapper.BranchMapper;
import com.accenture.assessment.branch.entity.BranchEntity;
import com.accenture.assessment.franchise.entity.FranchiseEntity;
import com.accenture.assessment.franchise.model.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FranchiseMapper {
    private final BranchMapper branchMapper;

    public Franchise toModel(FranchiseEntity franchiseEntity) {
        return new Franchise(
                franchiseEntity.getId(),
                franchiseEntity.getName(),
                List.of()
        );
    }

    public Franchise toModel(FranchiseEntity franchiseEntity, List<BranchEntity> branchEntities) {
        return new Franchise(
                franchiseEntity.getId(),
                franchiseEntity.getName(),
                branchEntities.stream()
                        .map(branchMapper::toModel)
                        .toList());
    }

    public FranchiseEntity map(Franchise franchise) {
        return FranchiseEntity.builder()
                .id(franchise.id())
                .name(franchise.name())
                .build();
    }

}
