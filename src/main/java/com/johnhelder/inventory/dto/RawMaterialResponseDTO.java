package com.johnhelder.inventory.dto;

import java.math.BigDecimal;

public record RawMaterialResponseDTO(
        Long id,
        String code,
        String name,
        Integer stockQuantity,
        String category,
        Integer minimumQuantity,
        String unit,
        String location,
        BigDecimal unitPrice
) {}
