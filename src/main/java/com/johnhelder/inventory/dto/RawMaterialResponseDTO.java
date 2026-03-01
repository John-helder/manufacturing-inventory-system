package com.johnhelder.inventory.dto;

public record RawMaterialResponseDTO(
        Long id,
        String code,
        String name,
        Integer quantity
) {}
