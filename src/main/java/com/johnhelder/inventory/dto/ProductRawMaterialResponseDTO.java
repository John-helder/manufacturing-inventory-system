package com.johnhelder.inventory.dto;

public record ProductRawMaterialResponseDTO(
        Long id,
        Long productId,
        String productName,
        Long rawMaterialId,
        String rawMaterialName,
        Integer quantityRequired
) {
}
