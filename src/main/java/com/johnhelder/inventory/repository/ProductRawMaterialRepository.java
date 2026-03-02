package com.johnhelder.inventory.repository;

import com.johnhelder.inventory.domain.Product;
import com.johnhelder.inventory.domain.ProductRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {
    List<ProductRawMaterial> findByProductId(Long productId);

    Optional<ProductRawMaterial> findByProductIdAndRawMaterialId(
            Long productId,
            Long rawMaterialId
    );
}
