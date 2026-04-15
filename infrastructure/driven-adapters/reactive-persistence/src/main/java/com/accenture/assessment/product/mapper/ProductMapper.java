package com.accenture.assessment.product.mapper;

import com.accenture.assessment.product.entity.ProductEntity;
import com.accenture.assessment.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toModel(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getBranchId(),
                productEntity.getName(),
                productEntity.getStock()
        );
    }
}
