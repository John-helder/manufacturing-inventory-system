package com.johnhelder.inventory.dto;

public record ProductProductionAvailabilityDTO(
        Long productId,
        String productName,
        boolean canProduce,
        Integer maxQuantityPossible
) {
}
