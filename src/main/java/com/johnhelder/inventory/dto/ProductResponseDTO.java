package com.johnhelder.inventory.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String code,
        String name,
        BigDecimal value
) {}
