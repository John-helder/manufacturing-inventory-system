package com.johnhelder.inventory.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank
        String code,

        @NotBlank
        String name,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal value

) {}
