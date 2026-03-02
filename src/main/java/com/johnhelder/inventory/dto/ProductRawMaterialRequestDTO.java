package com.johnhelder.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductRawMaterialRequestDTO(
        @NotNull
        Long productId,

        @NotNull
        Long rawMaterialId,

        @NotNull
        @Min(1)
        Integer quantityRequired
){}
