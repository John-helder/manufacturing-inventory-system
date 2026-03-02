package com.johnhelder.inventory.controller;

import com.johnhelder.inventory.dto.ProductRawMaterialRequestDTO;
import com.johnhelder.inventory.dto.ProductRawMaterialResponseDTO;
import com.johnhelder.inventory.service.ProductRawMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-raw-materials")
@RequiredArgsConstructor
public class ProductRawMaterialController {

    private final ProductRawMaterialService service;

    @PostMapping
    public ResponseEntity<ProductRawMaterialResponseDTO> create(
            @Valid @RequestBody ProductRawMaterialRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductRawMaterialResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRawMaterialResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRawMaterialResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRawMaterialRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
