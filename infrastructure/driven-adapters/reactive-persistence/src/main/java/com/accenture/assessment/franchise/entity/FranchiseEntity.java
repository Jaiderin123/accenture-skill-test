package com.accenture.assessment.franchise.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("franchises")
public class FranchiseEntity {
    @Id
    private UUID id;
    private String name;
}
