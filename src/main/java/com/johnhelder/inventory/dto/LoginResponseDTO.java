package com.johnhelder.inventory.dto;

public record LoginResponseDTO(
        String token,
        String username,
        String role
) {
}
