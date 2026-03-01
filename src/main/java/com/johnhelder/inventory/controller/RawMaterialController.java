package com.johnhelder.inventory.controller;


import com.johnhelder.inventory.dto.RawMaterialRequestDTO;
import com.johnhelder.inventory.dto.RawMaterialResponseDTO;
import com.johnhelder.inventory.service.RawMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
@RequiredArgsConstructor
public class RawMaterialController {

    private final RawMaterialService service;

    @PostMapping
    public ResponseEntity<RawMaterialResponseDTO> create(
            @Valid @RequestBody RawMaterialRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RawMaterialRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
