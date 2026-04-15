package com.accenture.assessment.product.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class ProductEntity {
    @Id
    private UUID id;
    private UUID branchId;
    private String name;
    private Integer stock;
}
