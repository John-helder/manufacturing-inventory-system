package com.johnhelder.inventory.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record RawMaterialRequestDTO(
        @NotBlank
        String code,

        @NotBlank
        String name,

        @NotNull
        @Min(0)
        Integer quantity,

        String category,

        @Min(0)
        Integer minimumQuantity,

        String unit,

        String location,

        @DecimalMin("0.0")
        BigDecimal unitPrice
) {}
