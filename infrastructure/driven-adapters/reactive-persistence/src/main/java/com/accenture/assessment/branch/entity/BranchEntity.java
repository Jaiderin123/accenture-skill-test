package com.accenture.assessment.branch.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("branches")
public class BranchEntity {
    @Id
    private UUID id;
    private UUID franchiseId;
    private String name;
}
